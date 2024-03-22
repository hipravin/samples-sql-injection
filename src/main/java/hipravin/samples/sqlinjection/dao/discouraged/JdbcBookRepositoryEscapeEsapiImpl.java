package hipravin.samples.sqlinjection.dao.discouraged;

import hipravin.samples.sqlinjection.dao.AbstractJdbcBookRepository;
import hipravin.samples.sqlinjection.dao.BookEntity;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;
import org.owasp.esapi.codecs.OracleCodec;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Vulnerable to SQL injection due to parameter string concatenation.
 */
@Repository
@Transactional
public class JdbcBookRepositoryEscapeEsapiImpl extends AbstractJdbcBookRepository {

    public JdbcBookRepositoryEscapeEsapiImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<BookEntity> findByTitle(String title) {
        String query = "select * from book where title = '" + escapeSpecialCharacters(title) + "'";
        return jdbcTemplate.query(query, BOOK_ROW_MAPPER);
    }

    static String escapeSpecialCharacters(String value) {
        //available codecs: MySQL, DB2, Oracle. But there is no PostgreSQL codec!
        return ESAPI.encoder().encodeForSQL(new OracleCodec(), value);
    }
}
