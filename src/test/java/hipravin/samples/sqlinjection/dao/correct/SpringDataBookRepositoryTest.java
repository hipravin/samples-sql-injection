package hipravin.samples.sqlinjection.dao.correct;

import hipravin.samples.sqlinjection.dao.BookEntity;
import hipravin.samples.sqlinjection.dao.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static hipravin.samples.sqlinjection.dao.BookRepositoryTestUtils.assertNotFoundByTitle;
import static hipravin.samples.sqlinjection.dao.BookRepositoryTestUtils.assertSingleResultByTitle;


@SpringBootTest
@ActiveProfiles({"test"})
class SpringDataBookRepositoryTest {
    @Autowired
    SpringDataBookRepository springDataBookRepository;

    BookRepository springDataMethodNameRepository = new BookRepository() {
        @Override
        public List<BookEntity> findByTitle(String title) {
            return springDataBookRepository.findByTitle(title);
        }
    };

    BookRepository springDataJpqlExplicitRepository = new BookRepository() {
        @Override
        public List<BookEntity> findByTitle(String title) {
            return springDataBookRepository.findByTitleExplicitJpql(title);
        }
    };

    @Test
    void testFind() {
        assertSingleResultByTitle(springDataMethodNameRepository, "Test Effective Java");
        assertSingleResultByTitle(springDataMethodNameRepository, "You Don't Know JS. Up & Going");

        assertSingleResultByTitle(springDataJpqlExplicitRepository, "Test Effective Java");
        assertSingleResultByTitle(springDataJpqlExplicitRepository, "You Don't Know JS. Up & Going");
    }

    @Test
    void testNotVulnerable() {
        String title = "' or '1'='1";
        assertNotFoundByTitle(springDataMethodNameRepository, title);
        assertNotFoundByTitle(springDataJpqlExplicitRepository, title);
    }

}