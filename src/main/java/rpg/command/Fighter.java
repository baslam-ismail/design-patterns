package rpg.command;

import rpg.core.CharacterProfile;

/**
 * État runtime d'un combattant pendant un duel.
 */
public class Fighter {
    private CharacterProfile profile;
    private int hp;
    private boolean defending;

    public Fighter(CharacterProfile baseProfile) {
        this.profile = baseProfile;
        this.hp = 10 + baseProfile.getPowerLevel(); // règle simple PV
        this.defending = false;
    }

    public CharacterProfile getProfile() { return profile; }
    public void setProfile(CharacterProfile profile) { this.profile = profile; }

    public int getHp() { return hp; }
    public boolean isAlive() { return hp > 0; }

    public boolean isDefending() { return defending; }
    public void setDefending(boolean defending) { this.defending = defending; }

    /** Applique des dégâts (réduction si posture Défense). */
    public int receiveDamage(int raw) {
        int dmg = Math.max(0, raw);
        if (defending) {
            dmg = Math.max(0, dmg / 2);
            defending = false; // reset après coup
        }
        this.hp = Math.max(0, this.hp - dmg);
        return dmg;
    }

    public String getName() { return profile.getName(); }
}
