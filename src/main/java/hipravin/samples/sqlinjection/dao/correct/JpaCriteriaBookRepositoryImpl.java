package hipravin.samples.sqlinjection.dao.correct;

import hipravin.samples.sqlinjection.dao.BookEntity;
import hipravin.samples.sqlinjection.dao.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.Arrays;
import java.util.List;

/**
 * Not vulnerable to SQL Injection due to usage of parametrised queries
 */
@Repository
@Transactional
public class JpaCriteriaBookRepositoryImpl implements BookRepository {
    private final EntityManager em;

    public JpaCriteriaBookRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<BookEntity> findByTitle(String title) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookEntity> cq = cb.createQuery(BookEntity.class);
        Root<BookEntity> bookRoot = cq.from(BookEntity.class);
        cq.select(bookRoot);
        cq.where(cb.equal(bookRoot.get("title"), title));
        TypedQuery<BookEntity> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public List<BookEntity> findByTitleStartingWithOrderByTitle(String prefix) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookEntity> cq = cb.createQuery(BookEntity.class);
        Root<BookEntity> bookRoot = cq.from(BookEntity.class);
        cq.select(bookRoot);
        cq.where(cb.like(bookRoot.get("title"), escape(prefix) + "%", '\\'));
        cq.orderBy(cb.asc(bookRoot.get("title")));
        TypedQuery<BookEntity> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public List<BookEntity> findByTitleLikeOr(String... orLikeTitles) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookEntity> cq = cb.createQuery(BookEntity.class);
        Root<BookEntity> bookRoot = cq.from(BookEntity.class);
        cq.select(bookRoot);

        var orLikePredicates = Arrays.stream(orLikeTitles)
                .map(t -> cb.like(bookRoot.get("title"), "%" + escape(t) + "%", '\\'))
                .toArray(Predicate[]::new);

        cq.where(cb.or(orLikePredicates));
        cq.orderBy(cb.asc(bookRoot.get("title")));
        TypedQuery<BookEntity> q = em.createQuery(cq);
        return q.getResultList();
    }
}
