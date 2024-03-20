package hipravin.samples.sqlinjection.dao.correct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static hipravin.samples.sqlinjection.dao.BookRepositoryTestUtils.assertNotFoundByTitle;
import static hipravin.samples.sqlinjection.dao.BookRepositoryTestUtils.assertSingleResultByTitle;

@SpringBootTest
@ActiveProfiles({"test"})
class JpaBookRepositoryImplTest {
    @Autowired
    JpaBookRepositoryImpl jpaBookRepository;

    @Test
    void testFind() {
        assertSingleResultByTitle(jpaBookRepository, "Test Effective Java");
        assertSingleResultByTitle(jpaBookRepository, "You Don't Know JS. Up & Going");
    }

    @Test
    void testNotVulnerable() {
        String title = "' or '1'='1";
        assertNotFoundByTitle(jpaBookRepository, title);
    }

}