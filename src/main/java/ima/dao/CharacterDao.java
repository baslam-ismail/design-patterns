package ima.dao;

import java.util.ArrayList;
import java.util.List;
import ima.core.Character;

public class CharacterDao implements Dao<Character> {
    private final List<Character> characters = new ArrayList<>();

    @Override
    public void save(Character item) {
        characters.add(item);
    }

    @Override
    public Character findByName(String name) {
        for (Character character : characters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;
    }

    @Override
    public List<Character> findAll() {
        return new ArrayList<>(characters);
    }
}
