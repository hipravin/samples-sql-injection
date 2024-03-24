package hipravin.samples.sqlinjection.dao.correct;

import hipravin.samples.sqlinjection.dao.BookEntity;
import hipravin.samples.sqlinjection.dao.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpringDataBookQueryImpl implements BookRepository {
    private final SpringDataBookRepository dataBookRepository;

    public SpringDataBookQueryImpl(SpringDataBookRepository dataBookRepository) {
        this.dataBookRepository = dataBookRepository;
    }


    @Override
    public List<BookEntity> findByTitle(String title) {
        return dataBookRepository.findByTitleJpql(title);
    }

    @Override
    public List<BookEntity> findByTitleStartingWithOrderByTitle(String prefix) {
        return dataBookRepository.findByTitleStartingJpql(prefix);
    }
}
