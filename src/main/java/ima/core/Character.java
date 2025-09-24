package ima.core;

import ima.builder.CharacterBuilder;
import ima.decorator.PersonnageAmeliore;

public class Character implements PersonnageAmeliore {
    private String name;
    private int strength, agility, intelligence;

    public Character(CharacterBuilder builder) {
        this.name = builder.getName();
        this.strength = builder.getStrength();
        this.agility = builder.getAgility();
        this.intelligence = builder.getIntelligence();
    }

    @Override
    public int getPowerLevel() {
        return this.strength + this.agility + this.intelligence;
    }

    @Override
    public String getDescription() {
        return this.name + " (Strength: " + this.strength + ", Agility: " + this.agility + ", Intelligence: "
                + this.intelligence + "). Son pouvoir est de " + this.getPowerLevel() + ".";
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getIntelligence() {
        return intelligence;
    }
}
