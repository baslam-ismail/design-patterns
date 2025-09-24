package ima.decorator;

public abstract class CharacterDecorator implements PersonnageAmeliore {
    protected PersonnageAmeliore decoratedCharacter;

    public CharacterDecorator(PersonnageAmeliore decoratedCharacter) {
        this.decoratedCharacter = decoratedCharacter;
    }

    @Override
    public int getPowerLevel() {
        return decoratedCharacter.getPowerLevel();
    }

    @Override
    public String getDescription() {
        return decoratedCharacter.getDescription();
    }
}
