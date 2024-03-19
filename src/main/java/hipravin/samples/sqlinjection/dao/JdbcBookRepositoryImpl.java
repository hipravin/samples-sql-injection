package hipravin.samples.sqlinjection.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Not vulnerable to SQL Injection due to usage of parametrised queries
 */
@Repository
public class JdbcBookRepositoryImpl extends AbstractJdbcBookRepository {
    public JdbcBookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<BookEntity> findByTitle(String title) {
        String query = "select * from book where title = ?";
        return jdbcTemplate.query(query, ps -> ps.setString(1, title),
                BOOK_ROW_MAPPER);
    }


}
