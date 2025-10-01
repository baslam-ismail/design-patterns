package rpg.decorator;

import rpg.core.CharacterProfile;

/**
 * Décorateur abstrait pour {@link CharacterProfile}.
 *
 */

public abstract class CharacterDecorator implements CharacterProfile {

    /** Cible décorée ; toutes les méthodes y délèguent par défaut. */
    protected final CharacterProfile decoratedCharacter;

    /**
     * @param decoratedCharacter profil à envelopper (doit être non null)
     */
    protected CharacterDecorator(CharacterProfile decoratedCharacter) {
        this.decoratedCharacter = decoratedCharacter;
    }

    /** Permet de dérouler la chaîne de décorateurs (utile pour la validation). */
    public CharacterProfile getDecorated() { return decoratedCharacter; }

    // --- Délégations par défaut : à surcharger dans les décorateurs concrets ---
    @Override public String getName() { return decoratedCharacter.getName(); }

    @Override public int getStrength() { return decoratedCharacter.getStrength(); }

    @Override public int getAgility() { return decoratedCharacter.getAgility(); }

    @Override public int getIntelligence() { return decoratedCharacter.getIntelligence(); }

    @Override public int getPowerLevel() { return decoratedCharacter.getPowerLevel(); }

    @Override public String getDescription() {
        return decoratedCharacter.getDescription();
    }
}
