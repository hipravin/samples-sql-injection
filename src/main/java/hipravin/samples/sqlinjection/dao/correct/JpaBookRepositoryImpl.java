package hipravin.samples.sqlinjection.dao.correct;

import hipravin.samples.sqlinjection.dao.BookEntity;
import hipravin.samples.sqlinjection.dao.BookRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Not vulnerable to SQL Injection due to usage of parametrised queries
 */
@Repository
@Transactional
public class JpaBookRepositoryImpl implements BookRepository {
    private final EntityManager em;

    public JpaBookRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<BookEntity> findByTitle(String title) {
        var query = em.createNamedQuery("BookEntity.findByTitle", BookEntity.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    @Override
    public List<BookEntity> findByTitleStartingWithOrderByTitle(String prefix) {
        var query = em.createNamedQuery("BookEntity.findByTitlePrefix", BookEntity.class);
        query.setParameter("titlePrefix", EscapeCharacter.DEFAULT.escape(prefix) + "%");
        return query.getResultList();
    }
}
