package hipravin.samples.sqlinjection.dao;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcBookRepositoryVulnerableImplIT {
    public static final Logger log = LoggerFactory.getLogger(JdbcBookRepositoryVulnerableImplIT.class);

    @Autowired
    JdbcBookRepositoryVulnerableImpl jdbcBookRepositoryVulnerable;


    @Test
    void testSampleFind1() {
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitle("Effective Java");
        log.info("Books found: " + books);
    }

    @Test
    void testSampleHackFindAll() {
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitle("' or '1'='1");
        log.info("Books found: " + books);
    }
    @Test
    void testSampleHackTruncateInvalid() {
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitle("';truncate book;");
        log.info("Books found: " + books);
    }
    @Test
    void testSampleHackTruncate() {
        List<BookEntity> books = jdbcBookRepositoryVulnerable.findByTitle("';truncate book; select '1");
        log.info("Books found: " + books);
    }

}