package hipravin.samples.sqlinjection.dao.correct;

import hipravin.samples.sqlinjection.dao.AbstractJdbcBookRepository;
import hipravin.samples.sqlinjection.dao.BookEntity;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    @Override
    public List<BookEntity> findByTitleStartingWithOrderByTitle(String prefix) {
        String query = "select * from book where title like ? escape '\\' order by title";

        return jdbcTemplate.query(query, ps -> ps.setString(1, EscapeCharacter.DEFAULT.escape(prefix) + "%"),
                BOOK_ROW_MAPPER);
    }

    @Override
    public List<BookEntity> findByTitleLikeOr(String... orLikeTitles) {
        String likePart = Stream.generate(() -> "title like ? escape '\\'")
                .limit(orLikeTitles.length)
                .collect(Collectors.joining(" or "));
        String query = "select * from book where " + likePart + " order by title";

        return jdbcTemplate.query(query, ps -> {
            for (int i = 0; i < orLikeTitles.length; i++) {
                String titlePartEscaped = String.format("%%%s%%", EscapeCharacter.DEFAULT.escape(orLikeTitles[i]));
                ps.setString(i + 1, titlePartEscaped);
            }
        }, BOOK_ROW_MAPPER);
    }
}
