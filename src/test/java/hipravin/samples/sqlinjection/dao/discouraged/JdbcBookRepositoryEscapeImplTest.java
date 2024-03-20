package hipravin.samples.sqlinjection.dao.discouraged;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static hipravin.samples.sqlinjection.dao.BookRepositoryTestUtils.assertNotFoundByTitle;
import static hipravin.samples.sqlinjection.dao.BookRepositoryTestUtils.assertSingleResultByTitle;

@SpringBootTest
@ActiveProfiles({"test"})
class JdbcBookRepositoryEscapeImplTest {
    @Autowired
    JdbcBookRepositoryEscapeImpl jdbcBookRepositoryEscape;

    @Test
    void testFind() {
        assertSingleResultByTitle(jdbcBookRepositoryEscape, "Test Effective Java");
        assertSingleResultByTitle(jdbcBookRepositoryEscape, "You Don't Know JS. Up & Going");
    }

    @Test
    void testNotVulnerable() {
        String title = "' or '1'='1";
        assertNotFoundByTitle(jdbcBookRepositoryEscape, title);
    }
}