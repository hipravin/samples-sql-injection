package hipravin.samples.sqlinjection.dao.correct;

import hipravin.samples.sqlinjection.dao.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataBookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByTitle(String title);

    List<BookEntity> findByTitleStartingWithOrderByTitle(String title); //implicitly escapes % and _

    @Query("select b from BookEntity b where b.title = :title")
    List<BookEntity> findByTitleJpql(String title);

    @Query("select b from BookEntity b where b.title like ?#{escape([0])}% escape ?#{escapeCharacter()} order by b.title")
    List<BookEntity> findByTitleStartingJpql(String prefix);

    @Query("select b from BookEntity b where b.title like :prefix% order by b.title")
    List<BookEntity> findByTitleStartingJpqlNoEscape(String prefix);
}
