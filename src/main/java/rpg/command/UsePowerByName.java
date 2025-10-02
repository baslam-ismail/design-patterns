package rpg.command;

import rpg.core.CharacterProfile;
import rpg.decorator.*; // Invisibility, Telepathy, FireResistance

/** Applique un décorateur au Fighter (par nom). power = "invisibility"|"telepathy"|"fire" */
public class UsePowerByName implements Command {
    private final String name, power; private final FighterRegistry reg;
    public UsePowerByName(String name, String power, FighterRegistry reg) {
        this.name = name; this.power = power.toLowerCase(); this.reg = reg;
    }

    @Override public String execute() {
        Fighter f = reg.get(name);
        CharacterProfile before = f.getProfile();
        CharacterProfile after  =
                switch (power) {
                    case "invisibility", "invisible" -> new Invisibility(before);
                    case "telepathy" -> new Telepathy(before);
                    case "fire", "fireresistance", "fire_resistance" -> new FireResistance(before);
                    default -> before;
                };
        f.setProfile(after);
        return String.format("%s utilise %s → PL %d → %d",
                before.getName(), power, before.getPowerLevel(), after.getPowerLevel());
    }
}
