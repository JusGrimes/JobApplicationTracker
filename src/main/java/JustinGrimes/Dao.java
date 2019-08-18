package JustinGrimes;


import java.util.List;

public interface Dao<T> {
    List<T> getAll();
    T getById(int id);
    void update(int id, T t);
    void delete(int id);
    void add(T t);

}
