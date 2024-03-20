package hipravin.samples.sqlinjection.dao.discouraged;

import hipravin.samples.sqlinjection.dao.AbstractJdbcBookRepository;
import hipravin.samples.sqlinjection.dao.BookEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Vulnerable to SQL injection due to parameter string concatenation.
 */
@Repository
public class JdbcBookRepositoryEscapeImpl extends AbstractJdbcBookRepository {

    public JdbcBookRepositoryEscapeImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<BookEntity> findByTitle(String title) {
        String query = "select * from book where title = '" + escapeSpecialCharacters(title) + "'";
        return jdbcTemplate.query(query, BOOK_ROW_MAPPER);
    }

    static String escapeSpecialCharacters(String value) {
        return escapeQuotes(value);
    }

    static String escapeQuotes(String value) {
        return (value != null) ? value.replace("'", "''") : null;
    }
}
