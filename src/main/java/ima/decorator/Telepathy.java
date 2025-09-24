package ima.decorator;

public class Telepathy extends CharacterDecorator {
    public Telepathy(PersonnageAmeliore decoratedCharacter) {
        super(decoratedCharacter);
    }

    @Override
    public int getPowerLevel() {
        return decoratedCharacter.getPowerLevel() + 30; // Augmente le niveau de puissance de 30
    }

    @Override
    public String getDescription() {
        return decoratedCharacter.getDescription() + " Il a maintenant le pouvoir de télépathie.";
    }
}
