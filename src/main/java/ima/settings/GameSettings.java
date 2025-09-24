package ima.settings;
import ima.core.Character;

public class GameSettings {
    private static GameSettings instance;
    private int maxStatsPoints;

    private GameSettings() {
        this.maxStatsPoints = 50;
    }

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    public boolean isValid(Character character) {
        int totalPoints = character.getStrength() + character.getAgility() + character.getIntelligence();
        return totalPoints <= maxStatsPoints;
    }
}
