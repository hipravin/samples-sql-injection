package hipravin.samples.sqlinjection.dao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryTestUtils {
    public static void assertSingleResultByTitle(BookRepository repository, String title) {
        List<BookEntity> books = repository.findByTitle(title);
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(title, books.get(0).getTitle());
    }

    public static void assertNotFoundByTitle(BookRepository repository, String title) {
        List<BookEntity> books = repository.findByTitle(title);
        assertNotNull(books);
        assertTrue(books.isEmpty());
    }
}
