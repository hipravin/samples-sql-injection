package hipravin.samples.sqlinjection.dao.correct;

import hipravin.samples.sqlinjection.dao.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataBookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByTitle(String title);

    @Query("select b from BookEntity b where b.title = :title")
    List<BookEntity> findByTitleExplicitJpql(String title);
}
