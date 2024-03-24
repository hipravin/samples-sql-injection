package hipravin.samples.sqlinjection.dao.vulnerable;

import hipravin.samples.sqlinjection.dao.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static hipravin.samples.sqlinjection.dao.BookRepositoryTestUtils.assertSingleResultByTitle;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class JpaBookRepositoryVulnerableImplTest {
    @Autowired
    JpaBookRepositoryVulnerableImpl jpaBookRepositoryVulnerable;

    @Test
    void testFind() {
        assertSingleResultByTitle(jpaBookRepositoryVulnerable, "Head First Java");
    }

    @Test
    void testInvalidSyntax() {
        assertThrows(DataAccessException.class, () -> {
            assertSingleResultByTitle(jpaBookRepositoryVulnerable, "You Don't Know JS. Up & Going");
        });
    }

    @Test
    void testVulnerable() {
        String title = "' or '1'='1";
        List<BookEntity> books = jpaBookRepositoryVulnerable.findByTitle(title);
        assertNotNull(books);
        assertEquals(7, books.size());
    }
}