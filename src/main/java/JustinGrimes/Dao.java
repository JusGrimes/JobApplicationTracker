package JustinGrimes;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    List<T> getAll() throws SQLException;
    void update(int id, T t);
    void delete(int id);
    void add(T t);

}
