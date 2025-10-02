package rpg.command;

import rpg.core.CharacterProfile;

public class AttackByName implements Command {
    private final String attackerName, defenderName;
    private FighterRegistry reg;
    public AttackByName(String a, String d, FighterRegistry reg) { this.attackerName = a; this.defenderName = d; this.reg = reg; }

    @Override
    public String execute() {
        Fighter att = reg.get(attackerName);
        Fighter def = reg.get(defenderName);
        CharacterProfile a = att.getProfile();
        CharacterProfile d = def.getProfile();

        // base: force vs agilité (comme avant)
        int base = a.getStrength() - (d.getAgility() / 2);

        // bonus lié à l'écart de power-level (les décorateurs deviennent utiles en combat)
        int bonus = Math.max(0, (a.getPowerLevel() - d.getPowerLevel()) / 10);

        // dégâts bruts
        int raw = base + bonus;

        // plancher 1 pour éviter des combats qui n'avancent pas
        int dealt = def.receiveDamage(Math.max(1, raw));

        return String.format("%s attaque %s : %d dégâts | HP %s=%d",
                a.getName(), d.getName(), dealt, d.getName(), def.getHp());
    }



}
