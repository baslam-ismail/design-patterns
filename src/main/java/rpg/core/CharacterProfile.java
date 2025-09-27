package rpg.core;

/**
 * Profil minimal d'un personnage (lecture seule).
 */

public interface CharacterProfile {
    String getName();
    int getStrength();
    int getAgility();
    int getIntelligence();
    int getPowerLevel();
    String getDescription();
}
