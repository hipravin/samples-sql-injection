package hipravin.samples.sqlinjection.dao.discouraged;

import hipravin.samples.sqlinjection.dao.AbstractJdbcBookRepository;
import hipravin.samples.sqlinjection.dao.BookEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Vulnerable to SQL injection due to parameter string concatenation.
 */
@Repository
@Transactional
public class JdbcBookRepositoryEscapeImpl extends AbstractJdbcBookRepository {

    public JdbcBookRepositoryEscapeImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<BookEntity> findByTitle(String title) {
        String query = "select * from book where title = '" + escapeQuotes(title) + "'";
        return jdbcTemplate.query(query, BOOK_ROW_MAPPER);
    }

    static String escapeQuotes(String value) {
        return (value != null) ? value.replace("'", "''") : null;
    }
}
