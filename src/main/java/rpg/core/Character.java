package rpg.core;


/**
 * Personnage immuable du jeu.
 * Implémente {@link CharacterProfile} (lecture seule + métriques).
 * La validation métier est faite en amont (Builder/GameSettings).
 */

public class Character implements CharacterProfile {

    // État immuable (assigné une seule fois au constructeur)
    private final String name;
    private final int strength;
    private final int agility;
    private final int intelligence;


    /**
     * Construit un personnage avec des stats figées.
     * Aucune validation ici pour garder la classe simple/prévisible.
     */
    public Character(String name, int strength, int agility, int intelligence) {
        this.name = name;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
    }

    // --- Accesseurs simples : lecture seule de l'état interne ---

    public String getName() { return name; }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getIntelligence() {
        return intelligence;
    }


    // --- CharacterProfile ---
    /**
     * Calcule un niveau de puissance “naïf” comme somme des trois stats.
     */
    @Override
    public int getPowerLevel() {
        return this.strength + this.agility + this.intelligence;
    }


    /**
     * Fournit une description compacte, utile pour l'UI, les logs ou le debug.
     * Format stable : {@code "<name> [STR=x, AGI=y, INT=z]"}.
     */
    @Override
    public String getDescription() {
        return String.format("%s [STR=%d, AGI=%d, INT=%d]",
                name, strength, agility, intelligence);
    }

}
