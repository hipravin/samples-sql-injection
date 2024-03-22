package hipravin.samples.sqlinjection.dao.correct;

import hipravin.samples.sqlinjection.dao.AbstractJdbcBookRepository;
import hipravin.samples.sqlinjection.dao.BookEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Not vulnerable to SQL Injection due to usage of parametrised queries
 */
@Repository
@Transactional
public class JdbcBookRepositoryImpl extends AbstractJdbcBookRepository {
    public JdbcBookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<BookEntity> findByTitle(String title) {
        String query = "select * from book where title = ?";

        return jdbcTemplate.query(query, ps -> ps.setString(1, title),
                BOOK_ROW_MAPPER);

//        return jdbcTemplate.query(query, ps -> {
//                    ps.setString(1, title);
//                    org.postgresql.PGStatement pgstmt = ps.unwrap(org.postgresql.PGStatement.class);
//                    System.out.println("Is Use server prepare: " + pgstmt.isUseServerPrepare());
//                },
//                BOOK_ROW_MAPPER);
    }


}
