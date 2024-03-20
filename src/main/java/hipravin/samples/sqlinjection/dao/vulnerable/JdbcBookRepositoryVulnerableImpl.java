package hipravin.samples.sqlinjection.dao.vulnerable;

import hipravin.samples.sqlinjection.dao.AbstractJdbcBookRepository;
import hipravin.samples.sqlinjection.dao.BookEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Vulnerable to SQL injection due to parameter string concatenation.
 */
@Repository
public class JdbcBookRepositoryVulnerableImpl extends AbstractJdbcBookRepository {

    public JdbcBookRepositoryVulnerableImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<BookEntity> findByTitle(String title) {
        String query = "select * from book where title = '" + title + "'";
        return jdbcTemplate.query(query, BOOK_ROW_MAPPER);
    }
}
