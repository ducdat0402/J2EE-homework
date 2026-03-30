package phattrienungdungvoi2ee.kiemtragiuaky.controller;


import phattrienungdungvoi2ee.kiemtragiuaky.model.Course;
import phattrienungdungvoi2ee.kiemtragiuaky.service.CategoryService;
import phattrienungdungvoi2ee.kiemtragiuaky.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired private CategoryService categoryService;
    @Autowired
    private CourseService courseService;

    @GetMapping({"/", "/home"})
    public String home(Model model,
                       @RequestParam(defaultValue = "0")  int page,
                       @RequestParam(defaultValue = "")   String keyword,
                       @RequestParam(defaultValue = "")   String sort,
                       @RequestParam(defaultValue = "0")  int categoryId) {

        Page<Course> coursePage = courseService.searchCourses(
                keyword, page, sort, categoryId);

        model.addAttribute("courses",      coursePage.getContent());
        model.addAttribute("currentPage",  page);
        model.addAttribute("totalPages",   coursePage.getTotalPages());
        model.addAttribute("keyword",      keyword);
        model.addAttribute("sort",         sort);
        model.addAttribute("categoryId",   categoryId);
        model.addAttribute("categories",   categoryService.getAll()); // ← danh sách category

        return "home";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "error/403";
    }
}

