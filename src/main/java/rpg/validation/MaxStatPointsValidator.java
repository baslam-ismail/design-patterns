package rpg.validation;

import rpg.core.CharacterProfile;
import rpg.settings.GameSettings;

public class MaxStatPointsValidator extends ValidationHandler {
    @Override
    protected void check(CharacterProfile c) {
        int sum = c.getStrength() + c.getAgility() + c.getIntelligence();
        int max = GameSettings.getInstance().getMaxStatPoints();
        if (sum > max) {
            throw new IllegalArgumentException(
                    "Validation: somme des stats (" + sum + ") > maxStatPoints (" + max + ")."
            );
        }
    }
}
