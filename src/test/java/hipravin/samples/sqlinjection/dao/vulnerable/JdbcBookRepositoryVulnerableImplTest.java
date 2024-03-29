package hipravin.samples.sqlinjection.dao.vulnerable;

import hipravin.samples.sqlinjection.dao.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static hipravin.samples.sqlinjection.dao.BookRepositoryTestUtils.assertSingleResultByTitle;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class JdbcBookRepositoryVulnerableImplTest {
    @Autowired
    JdbcBookRepositoryVulnerableImpl jdbcBookRepositoryVulnerable;

    @Test
    void testFind() {
        assertSingleResultByTitle(jdbcBookRepositoryVulnerable, "Head First Java");
    }

    @Test
    void testBadGrammar() {
        assertThrows(BadSqlGrammarException.class, () -> {
            assertSingleResultByTitle(jdbcBookRepositoryVulnerable, "You Don't Know JS. Up & Going");
        });
    }

    @Test
    void testVulnerable() {
        String title = "' or '1'='1";
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitle(title);
        assertNotNull(books);
        assertEquals(7, books.size());
    }

    @Test
    void testFindLikeMultiple1() {
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitleLikeOr("%", "_");
        assertEquals(1, books.size());
    }

    @Test
    void testFindLikeMultiple2() {
        assertThrows(BadSqlGrammarException.class, () -> {
            jdbcBookRepositoryVulnerable.findByTitleLikeOr("%Some", "Head", "Don't");
        });
    }

    @Test
    void testFindLikeMultiple3() {
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitleLikeOr("Some", "Head");
        assertEquals(5, books.size());
    }
}