package hipravin.samples.sqlinjection.dao.vulnerable;

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
public class JpaBookRepositoryVulnerableImpl implements BookRepository {
    private final EntityManager em;

    public JpaBookRepositoryVulnerableImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<BookEntity> findByTitle(String title) {
        String jpqlQuery = "select b from BookEntity b where b.title = '" + title + "'";
        var query = em.createQuery(jpqlQuery, BookEntity.class);
        return query.getResultList();
    }

    @Override
    public List<BookEntity> findByTitleStartingWithOrderByTitle(String prefix) {
        var query = em.createNamedQuery("BookEntity.findByTitlePrefix", BookEntity.class);
        query.setParameter("titlePrefix", prefix + "%");
        return query.getResultList();
    }
}
