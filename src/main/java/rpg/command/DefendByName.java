package rpg.command;

public class DefendByName implements Command {

    private final String name;
    private final FighterRegistry reg;

    public DefendByName(String name, FighterRegistry reg) { this.name = name; this.reg = reg; }

    @Override public String execute() {
        Fighter f = reg.get(name);
        f.setDefending(true);
        return f.getName() + " se met en défense.";
    }
}
