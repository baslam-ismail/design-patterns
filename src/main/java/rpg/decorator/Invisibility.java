package rpg.decorator;

import rpg.core.CharacterProfile;


/**
 * Décorateur ajoutant une capacité d'invisibilité à un personnage.
 * Effets :
 * - +25 au niveau de puissance (bonus fixe, à ajuster selon équilibrage).
 * - Ajoute une mention lisible dans la description.
 *
 */

public class Invisibility extends CharacterDecorator {
    /**
     * @param decoratedCharacter profil à enrichir (non null)
     */
    public Invisibility(CharacterProfile decoratedCharacter) {
        super(decoratedCharacter);
    }

    /** Bonus de puissance pour refléter l’avantage tactique de l’invisibilité. */
    @Override public int getPowerLevel() { return super.getPowerLevel() + 25; }

    /** Ajoute un tag indiquant la nouvelle capacité. */
    @Override public String getDescription() { return super.getDescription() + " + Capacité: Invisibilité"; }
}
