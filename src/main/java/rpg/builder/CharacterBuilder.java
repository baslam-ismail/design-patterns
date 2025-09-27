package rpg.builder;
import rpg.core.Character;
import rpg.settings.GameSettings;


/**
 * Builder pour créer un {@link Character}.
 * Validation finale via {@link GameSettings} dans {@link #build()}.
 */

public class CharacterBuilder {

    // --- État à configurer avant build() ---
    private String name;
    private int strength, agility, intelligence;


    // --- Setters fluents (chaînage lisible) ---
    public CharacterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CharacterBuilder setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public CharacterBuilder setAgility(int agility) {
        this.agility = agility;
        return this;
    }

    public CharacterBuilder setIntelligence(int intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    // --- Construction + validation ---

    /**
     * Construit le personnage puis vérifie les règles du jeu.
     * @return un {@code Character} valide
     * @throws IllegalArgumentException si les règles ne sont pas respectées
     */

    public Character build() {

        // Instancie le personnage avec l'état accumulé dans le builder
        Character c = new Character(name, strength, agility, intelligence);

        // Validation centralisée : on utilise le singleton des réglages du jeu
        if (!GameSettings.getInstance().isValid(c)) {

            // Message explicite pour aider au débogage et au feedback utilisateur
            throw new IllegalArgumentException("Règles du jeu non respectées pour " + name);
        }

        // Si tout est bon, on retourne l'objet construit
        return c;
    }


}
