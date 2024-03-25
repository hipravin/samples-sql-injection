package hipravin.samples.sqlinjection.dao.correct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static hipravin.samples.sqlinjection.dao.BookRepositoryTestUtils.*;
@SpringBootTest
@ActiveProfiles({"test"})
class JpaCriteriaBookRepositoryImplTest {
    @Autowired
    JpaCriteriaBookRepositoryImpl jpaCriteriaBookRepository;

    @Test
    void testFind() {
        testFindCorrect(jpaCriteriaBookRepository);
    }

    @Test
    void testStartingWith() {
        testFindLikeCorrect(jpaCriteriaBookRepository);
    }

    @Test
    void testFindLikeOr() {
        testFindLikeOrCorrect(jpaCriteriaBookRepository);
    }

}