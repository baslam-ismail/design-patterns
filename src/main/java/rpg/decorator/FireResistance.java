package rpg.decorator;

import rpg.core.CharacterProfile;
import rpg.settings.GameSettings;

/**
 * Décorateur ajoutant une résistance au feu à un personnage. Effets : - +30 au
 * niveau de puissance (valeur arbitraire/exemple). - Ajoute une mention dans la
 * description.
 *
 */
public class FireResistance extends CharacterDecorator {

    /**
     * @param decoratedCharacter profil à enrichir (non null)
     */
    public FireResistance(CharacterProfile decoratedCharacter) {
        super(decoratedCharacter);
    }

    /**
     * Augmente le niveau de puissance pour refléter la résistance. Ici, bonus
     * fixe de +30.
     */
    @Override
    public int getPowerLevel() {
        return super.getPowerLevel() + GameSettings.FIRE_RESISTANCE_BONUS;
    }

    /**
     * Ajoute un tag lisible indiquant la capacité supplémentaire.
     */
    @Override
    public String getDescription() {
        return super.getDescription() + " + Capacité: Résistance au feu";
    }
}
