package hipravin.samples.sqlinjection.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class JdbcBookRepositoryVulnerableImplTest {
    @Autowired
    JdbcBookRepositoryVulnerableImpl jdbcBookRepositoryVulnerable;

    @Test
    void testFind() {
        String title = "Test Effective Java";
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitle(title);
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(title, books.get(0).getTitle());
    }

    @Test
    void testVulnerable() {
        String title = "' or '1'='1";
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitle(title);
        assertNotNull(books);
        assertEquals(7, books.size());
    }
}