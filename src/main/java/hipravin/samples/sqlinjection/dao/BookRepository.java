package hipravin.samples.sqlinjection.dao;

import org.springframework.data.jpa.repository.query.EscapeCharacter;

import java.util.List;

public interface BookRepository {
    List<BookEntity> findByTitle(String title);

    List<BookEntity> findByTitleStartingWithOrderByTitle(String prefix);

    /**
     * where title like '%value1%' or title like '%value2%' or ...  or title like '%valueN%'
     */
    default List<BookEntity> findByTitleLikeOr(String... orLikeTitles) {
        throw new UnsupportedOperationException("Not impelemnted in this repository");
    }

    default String escape(String value) {
        return EscapeCharacter.DEFAULT.escape(value);
    }
}
