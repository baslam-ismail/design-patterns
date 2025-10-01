package rpg.validation;

import rpg.core.CharacterProfile;
import rpg.decorator.CharacterDecorator;

/** Règle d’exemple : limiter le NOMBRE de capacités décoratrices empilées. */
public class CapabilitiesCountValidator extends ValidationHandler {
    private final int maxCapabilities;

    public CapabilitiesCountValidator(int maxCapabilities) {
        this.maxCapabilities = maxCapabilities;
    }

    @Override
    protected void check(CharacterProfile c) {
        int count = countCapabilities(c);
        if (count > maxCapabilities) {
            throw new IllegalArgumentException(
                    "Validation: trop de capacités (" + count + " > " + maxCapabilities + ")."
            );
        }
    }

    private int countCapabilities(CharacterProfile c) {
        int n = 0;
        while (c instanceof CharacterDecorator dec) {
            n++;
            c = dec.getDecorated(); // ← nécessite le getter ajouté à CharacterDecorator
        }
        return n;
    }
}
