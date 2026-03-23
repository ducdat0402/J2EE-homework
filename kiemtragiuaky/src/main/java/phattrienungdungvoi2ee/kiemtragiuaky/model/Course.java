package phattrienungdungvoi2ee.kiemtragiuaky.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String image;

    @Column
    private int credits;

    @Column
    private String lecturer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
