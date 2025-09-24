package ima.dao;

import java.util.List;

public interface Dao<T> {
    void save(T item);
    T findByName(String name);
    List<T> findAll();
}