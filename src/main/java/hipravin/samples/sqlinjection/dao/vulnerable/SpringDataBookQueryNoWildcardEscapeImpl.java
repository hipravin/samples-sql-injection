package hipravin.samples.sqlinjection.dao.vulnerable;

import hipravin.samples.sqlinjection.dao.BookEntity;
import hipravin.samples.sqlinjection.dao.BookRepository;
import hipravin.samples.sqlinjection.dao.correct.SpringDataBookRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpringDataBookQueryNoWildcardEscapeImpl implements BookRepository {
    private final SpringDataBookRepository dataBookRepository;

    public SpringDataBookQueryNoWildcardEscapeImpl(SpringDataBookRepository dataBookRepository) {
        this.dataBookRepository = dataBookRepository;
    }

    @Override
    public List<BookEntity> findByTitle(String title) {
        return dataBookRepository.findByTitleJpql(title);
    }

    @Override
    public List<BookEntity> findByTitleStartingWithOrderByTitle(String prefix) {
        return dataBookRepository.findByTitleStartingJpqlNoEscape(prefix);
    }
}
