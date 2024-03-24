package hipravin.samples.sqlinjection.dao.correct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static hipravin.samples.sqlinjection.dao.BookRepositoryTestUtils.*;

@SpringBootTest
@ActiveProfiles({"test"})
class SpringDataBookMethodImplTest {
    @Autowired
    SpringDataBookMethodImpl springDataBookMethod;

    @Test
    void testFind() {
        testFindCorrect(springDataBookMethod);
    }

    @Test
    void testStartingWith() {
        testFindLikeCorrect(springDataBookMethod);
    }
}