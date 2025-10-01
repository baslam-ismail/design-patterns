package rpg.command;

import rpg.core.CharacterProfile;

/**
 * Attaquer : dégâts = STR_att - (AGI_def / 2), min 0.
 */
public class AttackCommand implements Command {
    private final Fighter attacker;
    private final Fighter defender;

    public AttackCommand(Fighter attacker, Fighter defender) {
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    public String execute() {
        CharacterProfile a = attacker.getProfile();
        CharacterProfile d = defender.getProfile();

        int raw = a.getStrength() - (d.getAgility() / 2);
        int dealt = defender.receiveDamage(Math.max(0, raw));

        return String.format("%s attaque %s : %d dégâts | HP %s=%d",
                a.getName(), d.getName(), dealt,
                d.getName(), defender.getHp());
    }
}
