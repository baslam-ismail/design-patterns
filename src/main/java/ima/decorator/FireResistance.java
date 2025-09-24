package ima.decorator;

public class FireResistance extends CharacterDecorator {
    public FireResistance(PersonnageAmeliore decoratedCharacter) {
        super(decoratedCharacter);
    }

    @Override
    public int getPowerLevel() {
        return decoratedCharacter.getPowerLevel() + 30; // Augmente le niveau de puissance de 30
    }

    @Override
    public String getDescription() {
        return decoratedCharacter.getDescription() + " Il a maintenant une résistance au feu.";
    }
}
