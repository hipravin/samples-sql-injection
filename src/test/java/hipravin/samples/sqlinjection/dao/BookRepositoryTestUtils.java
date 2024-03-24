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

    public static void assertFindByTitleStartingWith(
            BookRepository repository, String titlePrefix, int expectedResultSize) {
        List<BookEntity> books = repository.findByTitleStartingWithOrderByTitle(titlePrefix);
        assertNotNull(books);
        assertEquals(expectedResultSize, books.size());
        books.forEach(b -> assertTrue(b.getTitle().startsWith(titlePrefix)));
        //assert ordered, can't resist the desire of this little trick
        books.stream().reduce((a, b) -> {
            assertTrue(a.getTitle().compareTo(b.getTitle()) <= 0);
            return b;
        });
    }

    public static void assertFindByTitleStartingWithInjection(
            BookRepository repository, String titlePrefix, int expectedResultSize) {
        List<BookEntity> books = repository.findByTitleStartingWithOrderByTitle(titlePrefix);
        assertNotNull(books);
        assertEquals(expectedResultSize, books.size());

        books.stream().reduce((a, b) -> {
            assertTrue(a.getTitle().compareTo(b.getTitle()) <= 0);
            return b;
        });
    }

    public static void testFindCorrect(BookRepository repository) {
        assertSingleResultByTitle(repository, "Head First Java");
        assertSingleResultByTitle(repository, "You Don't Know JS. Up & Going");
        assertNotFoundByTitle(repository, "' or '1'='1");
    }

    public static void testFindVulnerable(BookRepository repository) {
        String title = "' or '1'='1";
        List<BookEntity> books = repository.findByTitle(title);
        assertNotNull(books);
        assertEquals(7, books.size());
    }

    public static void testFindLikeCorrect(BookRepository repository) {
        assertFindByTitleStartingWith(repository, "%Some", 0);
        assertFindByTitleStartingWith(repository, "Some", 3);
        assertFindByTitleStartingWith(repository, "%", 0);
        assertFindByTitleStartingWith(repository, "What if % or _ is present in the title?", 1);
    }

    public static void testFindLikeInjection(BookRepository repository) {
        assertFindByTitleStartingWith(repository, "Some", 3);
        assertFindByTitleStartingWithInjection(repository, "%Some", 4);
        assertFindByTitleStartingWithInjection(repository, "%", 7);
        assertFindByTitleStartingWith(repository, "What if % or _ is present in the title?", 1);
    }
}
