package ima.decorator;

public class Invisibility extends CharacterDecorator {
    public Invisibility(PersonnageAmeliore decoratedCharacter) {
        super(decoratedCharacter);
    }

    @Override
    public int getPowerLevel() {
        return decoratedCharacter.getPowerLevel() + 25; // Augmente le niveau de puissance de 25
    }

    @Override
    public String getDescription() {
        return decoratedCharacter.getDescription() + " Il a maintenant le pouvoir d'invisibilité.";
    }
}
