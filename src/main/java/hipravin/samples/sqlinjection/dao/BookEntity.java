package hipravin.samples.sqlinjection.dao;

import jakarta.persistence.*;


@Entity
@Table(name = "BOOK")
@NamedQueries({
        @NamedQuery(name = "BookEntity.findById",
                query="select b from BookEntity b where b.title = :title")
})
public class BookEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //identity generator is perfect until we require insert batching
    private Long id;

    @Basic
    @Column(name = "TITLE")
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
