package rpg.dao;

import rpg.core.CharacterProfile;

import java.util.*;

public class CharacterDao implements Dao<CharacterProfile> {
    private final Map<String, CharacterProfile> store = new HashMap<>();

    @Override
    public void save(CharacterProfile item) {
        Objects.requireNonNull(item, "item");
        store.put(item.getName(), item);
    }

    @Override
    public CharacterProfile findByName(String name) {
        return store.get(name);
    }

    @Override
    public List<CharacterProfile> findAll() {
        return List.copyOf(store.values());
    }
}
