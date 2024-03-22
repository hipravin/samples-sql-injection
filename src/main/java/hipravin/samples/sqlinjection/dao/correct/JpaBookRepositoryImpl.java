package hipravin.samples.sqlinjection.dao.correct;

import hipravin.samples.sqlinjection.dao.BookEntity;
import hipravin.samples.sqlinjection.dao.BookRepository;
import jakarta.persistence.EntityManager;
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
        var query = em.createNamedQuery("BookEntity.findById", BookEntity.class);
        query.setParameter("title", title);
        return query.getResultList();
    }
}
