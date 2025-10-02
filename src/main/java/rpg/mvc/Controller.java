package rpg.mvc;

import java.util.List;

/** Contrôleur MVC : orchestre les actions du menu. */
public class Controller {
    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view  = view;
    }

    public void run() {
        view.showHeader();
        int choice;
        do {
            view.showMenu();
            choice = view.readInt("Choix ?");
            switch (choice) {
                case 1 -> createCharacter();
                case 2 -> view.showCharacters("DAO :", model.listAll());
                case 3 -> applyCapability();
                case 4 -> addToParty();
                case 5 -> removeFromParty();
                case 6 -> view.showMessage("Total Party = " + model.totalPartyPower());
                case 7 -> view.showCharacters("Party par puissance :", model.partyByPowerDesc());
                case 8 -> view.showCharacters("Party par nom :", model.partyByNameAsc());
                case 9 -> validateCharacter();
                case 10 -> fightCompare();
                case 11 -> battleWithCommands();      // <<< Combat command -> on affiche le log UNE fois
                case 12 -> replayLastBattle();         // <<< Replay sur nouveaux Fighters
                case 0 -> view.showMessage("Bye!");
                default -> view.showMessage("Choix invalide.");
            }
        } while (choice != 0);
    }

    // --- Actions de menu “métier” classiques

    private void createCharacter() {
        String name = view.readString("Nom ?");
        int s = view.readInt("STR ?"), a = view.readInt("AGI ?"), i = view.readInt("INT ?");
        try {
            model.createCharacter(name, s, a, i);
            view.showMessage("Créé.");
        } catch (IllegalArgumentException ex) {
            view.showMessage("[Erreur] " + ex.getMessage());
        }
    }

    private void applyCapability() {
        String name = view.readString("Nom cible ?");
        String cap  = view.readString("Capacité (invisibility|telepathy|fire) ?");
        view.showMessage(model.applyCapability(name, cap) ? "OK" : "Échec.");
    }

    private void addToParty() {
        String name = view.readString("Nom à ajouter ?");
        view.showMessage(model.addToParty(name) ? "Ajouté." : "Introuvable.");
    }

    private void removeFromParty() {
        String name = view.readString("Nom à retirer ?");
        view.showMessage(model.removeFromParty(name) ? "Retiré." : "Pas dans la party.");
    }

    private void validateCharacter() {
        String name = view.readString("Nom à valider ?");
        Boolean ok = model.validateByName(name);
        view.showMessage(ok == null ? "Introuvable." : ("GameSettings -> " + (ok ? "OK" : "KO")));
    }

    private void fightCompare() {
        String a = view.readString("A ?"), b = view.readString("B ?");
        view.showMessage(model.fightCompare(a, b));
    }

    // --- Partie 3 demandée : combat command + replay

    /** Lance un combat basé Command et affiche le log UNE fois. */
    private void battleWithCommands() {
        String a = view.readString("Nom combattant A ?");
        String b = view.readString("Nom combattant B ?");
        List<String> log = model.startRecordedBattle(a, b);  // retourne le log
        view.showLog(log);
    }

    /** Rejoue le DERNIER combat enregistré sur de nouveaux Fighters. */
    private void replayLastBattle() {
        String a = view.readString("Nom A (replay) ?");
        String b = view.readString("Nom B (replay) ?");
        List<String> log = model.replayLastBattleOnFreshFighters(a, b);
        view.showLog(log);
    }
}
