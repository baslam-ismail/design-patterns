package rpg.decorator;

import rpg.core.CharacterProfile;
import rpg.settings.GameSettings;

/**
 * Décorateur ajoutant une capacité de télépathie à un personnage. Effets : -
 * +30 au niveau de puissance (bonus fixe, ajustable selon l'équilibrage). -
 * Ajoute une mention lisible dans la description.
 *
 */
public class Telepathy extends CharacterDecorator {

    /**
     * @param decoratedCharacter profil à enrichir (non null)
     */
    public Telepathy(CharacterProfile decoratedCharacter) {
        super(decoratedCharacter);
    }

    /**
     * Bonus de puissance pour refléter l'avantage conféré par la télépathie.
     */
    @Override
    public int getPowerLevel() {
        return super.getPowerLevel() + GameSettings.TELEPATHY_BONUS;
    }

    /**
     * Ajoute un tag indiquant la nouvelle capacité.
     */
    @Override
    public String getDescription() {
        return super.getDescription() + " + Capacité: Télépathie";
    }
}
