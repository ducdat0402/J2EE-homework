package phattrienungdungvoi2ee.kiemtragiuaky.controller;


import phattrienungdungvoi2ee.kiemtragiuaky.model.Course;
import phattrienungdungvoi2ee.kiemtragiuaky.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/courses")
public class CourseController {

    @Autowired private CourseService courseService;
    @Autowired private CategoryService categoryService;

    // Danh sách course cho admin
    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseService.getAll());
        return "admin/course/list";
    }

    // Form thêm mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("categories", categoryService.getAll());
        return "admin/course/add";
    }

    // Lưu course mới
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("course") Course course,
                       BindingResult result,
                       @RequestParam("imageFile") MultipartFile imageFile,
                       @RequestParam("category.id") int categoryId,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "admin/course/add";
        }
        courseService.saveImage(course, imageFile);
        course.setCategory(categoryService.getById(categoryId));
        courseService.save(course);
        return "redirect:/admin/courses";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Course course = courseService.getById(id);
        if (course == null) return "redirect:/admin/courses";
        model.addAttribute("course", course);
        model.addAttribute("categories", categoryService.getAll());
        return "admin/course/edit";
    }

    // Cập nhật
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("course") Course course,
                         BindingResult result,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         @RequestParam("category.id") int categoryId,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "admin/course/edit";
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            courseService.saveImage(course, imageFile);
        }
        course.setCategory(categoryService.getById(categoryId));
        courseService.save(course);
        return "redirect:/admin/courses";
    }

    // Xoá
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        courseService.delete(id);
        return "redirect:/admin/courses";
    }
}
