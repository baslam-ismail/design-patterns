package rpg.dao;

import java.util.*;
import rpg.core.CharacterProfile;

/**
 * DAO en mémoire pour les profils de personnages.
 * - Stockage clé→valeur indexé par le nom (String).
 * - Pas de persistance disque ; utile pour tests/démo.
 * - Non thread-safe.
 */

public class CharacterDao implements Dao<CharacterProfile> {

    /** Stockage en mémoire, indexé par nom exact (sensible à la casse). */
    private final Map<String, CharacterProfile> store = new HashMap<>();

    /**
     * Enregistre ou met à jour un profil.
     * Si un profil existe déjà avec le même nom, il sera remplacé.
     * @throws NullPointerException si item est null.
     */
    @Override
    public void save(CharacterProfile item) {
        Objects.requireNonNull(item, "item");
        store.put(item.getName(), item);
    }

    /**
     * Recherche par nom exact (sensible à la casse).
     * @return le profil ou null s'il n'existe pas.
     */
    @Override
    public CharacterProfile findByName(String name) {
        return store.get(name);
    }


    /**
     * Retourne une vue immuable de tous les profils.
     * La collection retournée ne peut pas être modifiée par l'appelant.
     */
    @Override
    public List<CharacterProfile> findAll() {
        return List.copyOf(store.values());
    }
}
