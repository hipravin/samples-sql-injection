package hipravin.samples.sqlinjection.dao.correct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static hipravin.samples.sqlinjection.dao.BookRepositoryTestUtils.*;

@SpringBootTest
@ActiveProfiles({"test"})
class SpringDataBookQueryImplTest {
    @Autowired
    SpringDataBookQueryImpl springDataBookQuery;

    @Test
    void testFind() {
        testFindCorrect(springDataBookQuery);
    }

    @Test
    void testStartingWith() {
        testFindLikeCorrect(springDataBookQuery);
    }
}


