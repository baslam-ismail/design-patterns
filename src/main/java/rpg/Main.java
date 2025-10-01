package rpg;

import rpg.builder.CharacterBuilder;
import rpg.command.AttackCommand;
import rpg.command.BattleEngine;
import rpg.command.CommandInvoker;
import rpg.command.DefendCommand;
import rpg.command.Fighter;
import rpg.command.UsePowerCommand;
import rpg.core.Army;
import rpg.core.Character;
import rpg.core.CharacterProfile;
import rpg.core.Party;
import rpg.dao.CharacterDao;
import rpg.decorator.FireResistance;
import rpg.decorator.Invisibility;
import rpg.decorator.Telepathy;
import rpg.settings.GameSettings;
import rpg.validation.CapabilitiesCountValidator;
import rpg.validation.MaxStatPointsValidator;
import rpg.validation.NameNotBlankValidator;
import rpg.validation.ValidationHandler;

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
            line("Construit (unexpected): " + tooStrong.getDescription());
        } catch (IllegalArgumentException ex) {
            line("[OK attendu] Échec de build: " + ex.getMessage());
        }

        // ===========================
        // 2) DÉCORATEURS (CAPACITÉS)
        // ===========================
        title("Ajout de capacités (Decorator)");
        CharacterProfile fireTelepathyHero = new FireResistance(new Telepathy(hero));
        CharacterProfile invisibleVillain  = new Invisibility(villain);

        line(fireTelepathyHero.getDescription() + " | PL=" + fireTelepathyHero.getPowerLevel());
        line(invisibleVillain.getDescription()  + " | PL=" + invisibleVillain.getPowerLevel());

        // Chaîne de validation (US 2.3 – Chain of Responsibility)
        //    Chaque maillon applique une règle puis passe au suivant.
        //    -> Nom non vide -> Total de points <= max -> Au plus 2 capacités empilées
        try {
            ValidationHandler chain = new NameNotBlankValidator();
            chain.linkWith(new MaxStatPointsValidator())
                    .linkWith(new CapabilitiesCountValidator(2)); // limite arbitraire: 2 pouvoirs max

            // On valide un perso nu et des persos décorés
            chain.validate(hero);
            chain.validate(fireTelepathyHero);
            chain.validate(invisibleVillain);
            System.out.println("Validation (chaîne) OK pour: hero, fireTelepathyHero, invisibleVillain");
        } catch (IllegalArgumentException ex) {
            // Si une règle échoue, on affiche le message lisible
            System.out.println("[VALIDATION KO] " + ex.getMessage());
        }

        // ===========================
        // 3) DAO (STOCKAGE EN MÉMOIRE)
        // ===========================
        title("DAO (en mémoire)");
        CharacterDao dao = new CharacterDao();
        dao.save(hero);
        dao.save(villain);
        dao.save(fireTelepathyHero);
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

        line("Tri par puissance décroissante :");
        party.sortByPowerDesc().forEach(c -> line(" - " + c.getName() + " -> " + c.getPowerLevel()));

        line("Tri par nom (A→Z) :");
        party.sortByNameAsc().forEach(c -> line(" - " + c.getName()));

        boolean removed = party.removeByName("villain");
        line("removeByName(\"villain\") -> " + removed);
        line("Membres après suppression :");
        party.getMembers().forEach(c -> line(" - " + c.getName()));

        // ===========================
        //  ARMEE (GROUPES DE PARTY)
        // ===========================

        title("Armée (groupes de Party)");
        Party party2 = new Party();
        Character strongHero = new CharacterBuilder()
                .setName("StrongHero")
                .setStrength(15)
                .setAgility(10)
                .setIntelligence(5)
                .build(); // somme = 30
        party2.add(strongHero);
        party2.add(new CharacterBuilder()
                .setName("SmartHero")
                .setStrength(5)
                .setAgility(5)
                .setIntelligence(15)
                .build()); // somme = 25
        
        line("Party 1 (total PL=" + party.getTotalPower() + "):");
        party.getMembers().forEach(c -> line(" - " + c.getName() +
                " (PL=" + c.getPowerLevel() + ")"));
        
        line("Party 2 (total PL=" + party2.getTotalPower() + "):");
        party2.getMembers().forEach(c -> line(" - " + c.getName()
                + " (PL=" + c.getPowerLevel() + ")"));
        
        Army army = new Army("L'armée des héros");
        army.addParty(party);
        army.addParty(party2);

        line("Armée: " + army.getName() + " (total PL=" + army.getPowerLevel() + ")");
        army.getParties().forEach(p -> {
            line(" Party (total PL=" + p.getTotalPower() + "):");
            p.getMembers().forEach(c -> line("  - " + c.getName()
                    + " (PL=" + c.getPowerLevel() + ")"));
        });

        // ===========================
        // 5) VALIDATION (GameSettings)
        // ===========================
        title("Validation (contraintes GameSettings)");
        line(hero.getName()   + " valide ? " + GameSettings.getInstance().isValid(hero));
        line(villain.getName()+ " valide ? " + GameSettings.getInstance().isValid(villain));
        line("fireTelepathyHero valide ? " + GameSettings.getInstance().isValid(fireTelepathyHero));
        line("invisibleVillain  valide ? " + GameSettings.getInstance().isValid(invisibleVillain));

        // ===========================
        // 6) COMBAT DÉMO (simple par PL)
        // ===========================
        title("Combat (démo simple)");
        line("Villain vs Hero");
        line(resolveFight(villain, hero));
        line("Villain PL=" + villain.getPowerLevel());
        line("Hero PL=" + hero.getPowerLevel());

        // ===========================
        // 6.bis) ACTIONS (Command)
        // ===========================
        title("Actions (Command)");

        Fighter fHero    = new Fighter(fireTelepathyHero);
        Fighter fVillain = new Fighter(invisibleVillain);

        CommandInvoker invoker = new CommandInvoker();
        invoker.execute(new DefendCommand(fVillain));
        invoker.execute(new AttackCommand(fHero, fVillain));
        invoker.execute(new UsePowerCommand(
                fHero, p -> new FireResistance(p), "Résistance au feu (stack)"
        ));
        invoker.execute(new AttackCommand(fVillain, fHero));
        invoker.execute(new UsePowerCommand(
                fVillain, p -> new Telepathy(p), "Télépathie"
        ));
        invoker.execute(new AttackCommand(fHero, fVillain));

        line("État final : " + fHero.getName() + " HP=" + fHero.getHp()
                + " | " + fVillain.getName() + " HP=" + fVillain.getHp());

        // ===========================
        // 7) IMMUTABILITÉ DES VUES
        // ===========================
        title("Immutabilité des vues (optionnel)");
        try {
            party.getMembers().add(hero);
            line("[unexpected] la liste est modifiable !");
        } catch (UnsupportedOperationException ex) {
            line("[OK attendu] getMembers() retourne une vue immuable.");
        }

        // ===========================
        // 8) COMBAT AVANCÉ (BattleEngine + Replay)
        // ===========================
        title("Combat avancé (BattleEngine + historique/replay)");

        BattleEngine engine = new BattleEngine(fireTelepathyHero, invisibleVillain);
        engine.runBattle();      // exécution du combat
        engine.replayBattle();   // replay de l’historique
    }

    // --------- Helpers d'affichage ---------

    private static void title(String text) {
        System.out.println("\n=== " + text + " ===");
    }

    private static void line(String text) {
        System.out.println(text);
    }

    // Diff simple : gagnant par PL
    private static String resolveFight(CharacterProfile a, CharacterProfile b) {
        int diff = a.getPowerLevel() - b.getPowerLevel();
        if (diff == 0) return "Égalité entre " + a.getName() + " et " + b.getName();
        return (diff > 0 ? a.getName() : b.getName()) + " gagne (|Δ|=" + Math.abs(diff) + ")";
    }
}
