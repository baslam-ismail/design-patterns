package rpg;

import rpg.builder.CharacterBuilder;
import rpg.core.Character;
import rpg.core.CharacterProfile;
import rpg.core.Party;
import rpg.dao.CharacterDao;
import rpg.decorator.FireResistance;
import rpg.decorator.Invisibility;
import rpg.decorator.Telepathy;
import rpg.settings.GameSettings;

public class Main {

    public static void main(String[] args) {
        // ===========================
        // 0) RÉGLAGES GÉNÉRAUX
        // ===========================
        title("Réglages");
        GameSettings.getInstance().setMaxStatPoints(35); // budget de points total autorisé
        line("MaxStatPoints = " + GameSettings.getInstance().getMaxStatPoints());

        // ===========================
        // 1) CONSTRUCTION (BUILDER)
        // ===========================
        title("Construction de personnages (Builder)");

        Character hero = new CharacterBuilder()
                .setName("Hero")
                .setStrength(10)
                .setAgility(8)
                .setIntelligence(6)
                .build(); // somme = 24

        Character villain = new CharacterBuilder()
                .setName("Villain")
                .setStrength(8)
                .setAgility(6)
                .setIntelligence(5)
                .build(); // somme = 19

        // Cas d'échec (dépasse le budget) → IllegalArgumentException
        try {
            Character tooStrong = new CharacterBuilder()
                    .setName("TooStrongHero")
                    .setStrength(50)
                    .setAgility(50)
                    .setIntelligence(50)
                    .build();
            // ne devrait pas passer
            line("Construit (unexpected): " + tooStrong.getDescription());
        } catch (IllegalArgumentException ex) {
            line("[OK attendu] Échec de build: " + ex.getMessage());
        }

        // ===========================
        // 2) DÉCORATEURS (CAPACITÉS)
        // ===========================
        title("Ajout de capacités (Decorator)");
        // empilement possible : Télépathie puis Résistance au feu
        CharacterProfile fireTelepathyHero = new FireResistance(new Telepathy(hero));
        CharacterProfile invisibleVillain  = new Invisibility(villain);

        // Description + PL reflètent le décorateur (bonus fixes)
        line(fireTelepathyHero.getDescription() + " | PL=" + fireTelepathyHero.getPowerLevel());
        line(invisibleVillain.getDescription()  + " | PL=" + invisibleVillain.getPowerLevel());

        // ===========================
        // 3) DAO (STOCKAGE EN MÉMOIRE)
        // ===========================
        title("DAO (en mémoire)");
        CharacterDao dao = new CharacterDao();
        dao.save(hero);                 // stocke par nom exact
        dao.save(villain);
        dao.save(fireTelepathyHero);    // on peut stocker n'importe quel CharacterProfile
        dao.save(invisibleVillain);

        line("FindAll (nom -> PL)");
        dao.findAll().forEach(c -> line(" - " + c.getName() + " -> " + c.getPowerLevel()));

        line("findByName(\"Hero\") -> " + dao.findByName("Hero").getDescription());

        // ===========================
        // 4) PARTY (GROUPE DE PERSOS)
        // ===========================
        title("Party (groupe)");
        Party party = new Party();
        party.add(hero);
        party.add(villain);

        line("Membres (ordre d'ajout) :");
        party.getMembers().forEach(c -> line(" - " + c.getDescription()));

        line("Puissance totale de l’équipe (somme des PL) : " + party.getTotalPower());

        line("Tri par puissance décroissante (n'affecte pas la liste interne) :");
        party.sortByPowerDesc().forEach(c -> line(" - " + c.getName() + " -> " + c.getPowerLevel()));

        line("Tri par nom (A→Z) :");
        party.sortByNameAsc().forEach(c -> line(" - " + c.getName()));

        // Démonstration de removeByName (insensible à la casse)
        boolean removed = party.removeByName("villain");
        line("removeByName(\"villain\") -> " + removed);
        line("Membres après suppression :");
        party.getMembers().forEach(c -> line(" - " + c.getName()));

        // ===========================
        // 5) VALIDATION (GameSettings)
        // ===========================
        title("Validation (contraintes GameSettings)");
        // sur objets "nus"
        line(hero.getName()   + " valide ? " + GameSettings.getInstance().isValid(hero));
        line(villain.getName()+ " valide ? " + GameSettings.getInstance().isValid(villain));

        // sur objets décorés (les bonus de PL comptent dans la validation)
        line("fireTelepathyHero valide ? " + GameSettings.getInstance().isValid(fireTelepathyHero));
        line("invisibleVillain  valide ? " + GameSettings.getInstance().isValid(invisibleVillain));

        // ===========================
        // 6) COMBAT DÉMO
        // ===========================
        title("Combat (démo simple)");
        // Villain a été retiré de la Party ci-dessus, mais l’objet existe toujours ici
        line("Villain vs Hero");
        line(resolveFight(villain, hero));
        line("Villain PL=" + villain.getPowerLevel());
        line("Hero PL=" + hero.getPowerLevel());

        // ===========================
        // 7) (OPTION) TESTER L'IMMUTABILITÉ DES VUES
        // ===========================
        title("Immutabilité des vues (optionnel)");
        try {
            party.getMembers().add(hero); // doit lever UnsupportedOperationException
            line("[unexpected] la liste est modifiable !");
        } catch (UnsupportedOperationException ex) {
            line("[OK attendu] getMembers() retourne une vue immuable.");
        }
    }

    // --------- Helpers d'affichage (lisibilité console) ---------

    private static void title(String text) {
        System.out.println("\n=== " + text + " ===");
    }

    private static void line(String text) {
        System.out.println(text);
    }

    // Diff simple : gagnant par PL (égalité possible)
    private static String resolveFight(CharacterProfile a, CharacterProfile b) {
        int diff = a.getPowerLevel() - b.getPowerLevel();
        if (diff == 0) return "Égalité entre " + a.getName() + " et " + b.getName();
        return (diff > 0 ? a.getName() : b.getName()) + " gagne (|Δ|=" + Math.abs(diff) + ")";
    }
}
