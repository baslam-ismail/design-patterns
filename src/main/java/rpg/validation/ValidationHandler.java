package rpg.validation;

import rpg.core.CharacterProfile;

/** Chaîne de responsabilité : chaque maillon valide et passe au suivant. */
public abstract class ValidationHandler {
    private ValidationHandler next;

    /** Lie ce maillon au suivant, et retourne le suivant pour chainage fluide. */
    public ValidationHandler linkWith(ValidationHandler next) {
        this.next = next;
        return next;
    }

    /** Lance la validation complète de la chaîne. */
    public final void validate(CharacterProfile c) {
        check(c);
        if (next != null) next.validate(c);
    }

    /** Règle concrète à implémenter par chaque maillon. */
    protected abstract void check(CharacterProfile c);
}
