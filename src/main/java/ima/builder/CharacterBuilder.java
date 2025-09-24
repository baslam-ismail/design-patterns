package ima.builder;
import ima.core.Character;

public class CharacterBuilder {
    private String name;
    private int strength, agility, intelligence;

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

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public Character build() {
        return new Character(this);
    }

    public int getIntelligence() {
        return intelligence;
    }
}
