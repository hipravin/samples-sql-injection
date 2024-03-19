package hipravin.samples.sqlinjection.dao;


public class BookEntity {
    private Long id;
    private String title;

    public BookEntity() {
    }

    public BookEntity(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
