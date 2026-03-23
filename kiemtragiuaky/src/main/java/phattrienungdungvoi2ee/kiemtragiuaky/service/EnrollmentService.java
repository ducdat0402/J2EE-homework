package phattrienungdungvoi2ee.kiemtragiuaky.service;


import phattrienungdungvoi2ee.kiemtragiuaky.model.*;
import phattrienungdungvoi2ee.kiemtragiuaky.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired private EnrollmentRepository enrollmentRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private CourseRepository courseRepository;

    // Đăng ký học phần
    public String enroll(String username, int courseId) {
        // Kiểm tra đã đăng ký chưa
        if (enrollmentRepository.existsByStudent_UsernameAndCourse_Id(username, courseId)) {
            return "Bạn đã đăng ký học phần này rồi!";
        }

        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học phần"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollDate(LocalDate.now());

        enrollmentRepository.save(enrollment);
        return "success";
    }

    // Lấy danh sách học phần đã đăng ký
    public List<Enrollment> getEnrollmentsByUsername(String username) {
        return enrollmentRepository.findByStudent_Username(username);
    }
}
