package rpg.command;

/** Défense : réduit de 50% les prochains dégâts subis (une seule fois). */
public class DefendCommand implements Command {
    private final Fighter defender;

    public DefendCommand(Fighter defender) {
        this.defender = defender;
    }

    @Override
    public String execute() {
        defender.setDefending(true);
        return defender.getName() + " se met en défense.";
    }
}
