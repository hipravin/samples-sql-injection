package hipravin.samples.sqlinjection.showcase;

import hipravin.samples.sqlinjection.dao.BookEntity;
import hipravin.samples.sqlinjection.dao.correct.JdbcBookRepositoryImpl;
import hipravin.samples.sqlinjection.dao.correct.JpaBookRepositoryImpl;
import hipravin.samples.sqlinjection.dao.discouraged.JdbcBookRepositoryEscapeEsapiImpl;
import hipravin.samples.sqlinjection.dao.discouraged.JdbcBookRepositoryEscapeImpl;
import hipravin.samples.sqlinjection.dao.vulnerable.JdbcBookRepositoryVulnerableImpl;
import hipravin.samples.sqlinjection.dao.vulnerable.JpaBookRepositoryVulnerableImpl;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PlaygroundIT {
    public static final Logger log = LoggerFactory.getLogger(PlaygroundIT.class);

    @Autowired
    JdbcBookRepositoryVulnerableImpl jdbcBookRepositoryVulnerable;
    @Autowired
    JdbcBookRepositoryEscapeImpl jdbcBookRepositoryEscape;
    @Autowired
    JpaBookRepositoryVulnerableImpl jpaBookRepositoryVulnerable;
    @Autowired
    JdbcBookRepositoryImpl jdbcBookRepository;
    @Autowired
    JpaBookRepositoryImpl jpaBookRepository;
    @Autowired
    JdbcBookRepositoryEscapeEsapiImpl jdbcBookRepositoryEscapeEsapi;

    @RepeatedTest(10)
    void testServerPrepared() {
        List<String> titles = List.of("Sample book 1", "Sample Book 2", "Sample Book 3", "Effective java", "O'reilly");
        for (String title : titles) {
            executeQuietly(() -> jdbcBookRepository.findByTitle(title));
        }
    }

    @RepeatedTest(10)
    void testFillUserStatements() {
        List<String> titles = List.of("Sample book 1", "Sample Book 2", "Sample Book 3", "Effective java", "O'reilly");
        for (String title : titles) {
            executeQuietly(() -> jdbcBookRepositoryVulnerable.findByTitle(title));
            executeQuietly(() -> jdbcBookRepository.findByTitle(title));
            executeQuietly(() -> jpaBookRepository.findByTitle(title));
            executeQuietly(() -> jpaBookRepositoryVulnerable.findByTitle(title));
            executeQuietly(() -> jdbcBookRepositoryEscape.findByTitle(title));
            executeQuietly(() -> jdbcBookRepositoryEscapeEsapi.findByTitle(title));
        }
    }

    void executeQuietly(Executable executable) {
        try {
            executable.execute();
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
    }

    @Test
    void testSampleFind1() {
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitle("Effective Java");
        assertNotNull(books);
        log.info("Books found: " + books);
    }
    @Test
    void testSampleFindUnicode() {
        List<BookEntity> books = jdbcBookRepositoryEscape.findByTitle("\u02bc");
        assertNotNull(books);
        log.info("Books found: " + books);
    }

    @Test
    void testSampleHackFindAll() {
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitle("' or '1'='1");
        assertNotNull(books);
        log.info("Books found: " + books);
    }

    @Test
    void testSampleHackTruncateInvalid() {
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitle("';truncate book;");
        assertNotNull(books);
        log.info("Books found: " + books);
    }

    @Test
    void testSampleHackTruncate() {
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitle("';truncate book; select '1");
        assertNotNull(books);
        log.info("Books found: " + books);
    }
    @Test
    void testSampleHackDelete() {
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitle("';delete from book where '1'='1");
        assertNotNull(books);
        log.info("Books found: " + books);
    }

    @Test
    void testSampleHackDeleteJpa() {
        List<BookEntity> books = jpaBookRepositoryVulnerable.findByTitle("';delete from BookEntity where '1'='1");
        assertNotNull(books);
        log.info("Books found: " + books);
    }

    @Test
    void testSampleHackFindJpa() {
        List<BookEntity> books = jpaBookRepositoryVulnerable.findByTitle("' or '1'='1");
        assertNotNull(books);
        log.info("Books found: " + books);
    }
}