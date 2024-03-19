package hipravin.samples.sqlinjection.dao;

import java.util.List;

public interface BookRepository {
    List<BookEntity> findByTitle(String title);
}
