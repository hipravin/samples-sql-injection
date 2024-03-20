package hipravin.samples.sqlinjection.dao;

import hipravin.samples.sqlinjection.dao.vulnerable.JdbcBookRepositoryVulnerableImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static hipravin.samples.sqlinjection.dao.JdbcUtils.getLongNullable;

public abstract class AbstractJdbcBookRepository implements BookRepository {
    protected final JdbcTemplate jdbcTemplate;

    public AbstractJdbcBookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected final RowMapper<BookEntity> BOOK_ROW_MAPPER = JdbcBookRepositoryVulnerableImpl::mapBookRow;

    protected static class BookColumns {
        static final String ID = "ID";
        static final String TITLE = "TITLE";
    }

    protected static BookEntity mapBookRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = getLongNullable(rs, BookColumns.ID);
        String name1 = rs.getString(BookColumns.TITLE);
        return new BookEntity(id, name1);
    }
}
