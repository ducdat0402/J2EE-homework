package phattrienungdungvoi2ee.kiemtragiuaky.repository;


import phattrienungdungvoi2ee.kiemtragiuaky.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    // Kiểm tra sinh viên đã đăng ký học phần này chưa
    boolean existsByStudent_UsernameAndCourse_Id(String username, int courseId);

    // Lấy danh sách học phần đã đăng ký của sinh viên
    List<Enrollment> findByStudent_Username(String username);
}
