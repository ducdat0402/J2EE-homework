package phattrienungdungvoi2ee.kiemtragiuaky.service;


import phattrienungdungvoi2ee.kiemtragiuaky.model.Course;
import phattrienungdungvoi2ee.kiemtragiuaky.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Page<Course> getCourses(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return courseRepository.findAll(pageable);
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course getById(int id) {
        return courseRepository.findById(id).orElse(null);
    }
    // Tìm kiếm có phân trang
    public Page<Course> searchCourses(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        if (keyword == null || keyword.trim().isEmpty()) {
            return courseRepository.findAll(pageable);
        }
        return courseRepository.searchByName(keyword.trim(), pageable);
    }
    public void save(Course course) {
        courseRepository.save(course);
    }

    public void delete(int id) {
        courseRepository.deleteById(id);
    }

    public void saveImage(Course course, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Path dirImages = Paths.get("static/images");
                if (!Files.exists(dirImages)) Files.createDirectories(dirImages);
                String newFileName = UUID.randomUUID() + "_"
                        + imageFile.getOriginalFilename();
                Path pathUpload = dirImages.resolve(newFileName);
                Files.copy(imageFile.getInputStream(), pathUpload,
                        StandardCopyOption.REPLACE_EXISTING);
                course.setImage(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}