package rpg.validation;

import rpg.core.CharacterProfile;

public class NameNotBlankValidator extends ValidationHandler {
    @Override
    protected void check(CharacterProfile c) {
        var name = c.getName();
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Validation: le nom est vide.");
        }
    }
}
