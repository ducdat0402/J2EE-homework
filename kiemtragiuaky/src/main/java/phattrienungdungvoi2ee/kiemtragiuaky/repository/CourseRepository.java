package phattrienungdungvoi2ee.kiemtragiuaky.repository;

import phattrienungdungvoi2ee.kiemtragiuaky.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    Page<Course> findAll(Pageable pageable);

    // Tìm kiếm theo tên (không phân biệt hoa thường)
    @Query("SELECT c FROM Course c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Course> searchByName(String keyword, Pageable pageable);
}