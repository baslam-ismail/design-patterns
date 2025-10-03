package rpg.settings;
import rpg.core.CharacterProfile;


/**
 * Paramétrage global du jeu (singleton).
 * - Source de vérité pour les règles simples (ex. budget total de points).
 * - Accessible partout via getInstance().
 *
 */
public class GameSettings {
    public static final int FIRE_RESISTANCE_BONUS = 30;
    public static final int INVISIBILITY_BONUS = 25;
    public static final int TELEPATHY_BONUS = 30;

    /** Instance unique (initialisée au chargement de la classe). */
    private static final  GameSettings INSTANCE = new GameSettings();

    /** Budget total autorisé (somme des stats) par défaut. */
    private int maxStatPoints = 30;

    /** Constructeur privé : empêche l'instanciation directe. */
    private GameSettings() {}

    /** Accès à l'instance unique. */
    public static GameSettings getInstance() {
        return INSTANCE;
    }

    /** @return le budget total autorisé. */
    public int getMaxStatPoints() {
        return maxStatPoints;
    }

    /**
     * Modifie le budget total autorisé.
     * À utiliser par un admin, un écran d'options, ou les tests.
     */
    public void setMaxStatPoints(int maxStatPoints) {
        this.maxStatPoints = maxStatPoints;
    }

    /**
     * Valide un personnage selon les règles courantes.
     *
     * Actuellement : on compare la somme (getPowerLevel) au budget max.
     * ⚠ les bonus des décorateurs sont pas inclus.
     *
     */
    public boolean isValid(CharacterProfile character) {
        int totalPoints = character.getPowerLevel();
        return totalPoints <= maxStatPoints;
    }
}
