package rpg.dao;

import java.util.List;

/**
 * Contrat minimal d'un DAO générique en mémoire ou persistant.
 * @param <T> type d'entité manipulée
 */

public interface Dao<T> {
    /** Crée ou met à jour une entité. */
    void save(T item);

    /** Recherche une entité par nom exact */
    T findByName(String name);

    /** Retourne toutes les entités */
    List<T> findAll();
}