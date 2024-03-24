package hipravin.samples.sqlinjection.showcase;

import hipravin.samples.sqlinjection.dao.correct.JdbcBookRepositoryImpl;
import hipravin.samples.sqlinjection.dao.correct.JpaBookRepositoryImpl;
import hipravin.samples.sqlinjection.dao.correct.SpringDataBookMethodImpl;
import hipravin.samples.sqlinjection.dao.correct.SpringDataBookQueryImpl;
import hipravin.samples.sqlinjection.dao.discouraged.JdbcBookRepositoryEscapeEsapiImpl;
import hipravin.samples.sqlinjection.dao.discouraged.JdbcBookRepositoryEscapeImpl;
import hipravin.samples.sqlinjection.dao.vulnerable.JdbcBookRepositoryVulnerableImpl;
import hipravin.samples.sqlinjection.dao.vulnerable.JpaBookRepositoryVulnerableImpl;
import hipravin.samples.sqlinjection.dao.vulnerable.SpringDataBookQueryNoWildcardEscapeImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static hipravin.samples.sqlinjection.dao.BookRepositoryTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostgreSqlTestsIT {
    public static final Logger log = LoggerFactory.getLogger(PostgreSqlTestsIT.class);

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
    @Autowired
    SpringDataBookMethodImpl springDataBookMethod;
    @Autowired
    SpringDataBookQueryImpl springDataBookQuery;
    @Autowired
    SpringDataBookQueryNoWildcardEscapeImpl springDataBookQueryNoWildcardEscape;

    @Test
    void testFindByTitleExactCorrect() {
        testFindCorrect(springDataBookMethod);
        testFindCorrect(jdbcBookRepository);
        testFindCorrect(jpaBookRepository);
        testFindCorrect(springDataBookMethod);
        testFindCorrect(springDataBookQuery);
    }

    @Test
    void testLikeCorrect() {
        testFindLikeCorrect(springDataBookMethod);
        testFindLikeCorrect(jdbcBookRepository);
        testFindLikeCorrect(jpaBookRepository);
        testFindLikeCorrect(springDataBookMethod);
        testFindLikeCorrect(springDataBookQuery);
    }

    @Test
    void testFindByTitleInjection() {
        testFindVulnerable(jdbcBookRepositoryVulnerable);
        testFindVulnerable(jpaBookRepositoryVulnerable);
    }

    @Test
    void testLikeInjection() {
        testFindLikeInjection(jdbcBookRepositoryVulnerable);
        testFindLikeInjection(jpaBookRepositoryVulnerable);
        testFindLikeInjection(springDataBookQueryNoWildcardEscape);
    }
}