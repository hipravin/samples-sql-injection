package hipravin.samples.sqlinjection.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static hipravin.samples.sqlinjection.dao.JdbcUtils.getLongNullable;

public abstract class AbstractJdbcBookRepository implements BookRepository {
    final JdbcTemplate jdbcTemplate;

    public AbstractJdbcBookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    final RowMapper<BookEntity> BOOK_ROW_MAPPER = JdbcBookRepositoryVulnerableImpl::mapBookRow;

    static class BookColumns {
        static final String ID = "ID";
        static final String TITLE = "TITLE";
    }
    static BookEntity mapBookRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = getLongNullable(rs, BookColumns.ID);
        String name1 = rs.getString(BookColumns.TITLE);
        return new BookEntity(id, name1);
    }
}
