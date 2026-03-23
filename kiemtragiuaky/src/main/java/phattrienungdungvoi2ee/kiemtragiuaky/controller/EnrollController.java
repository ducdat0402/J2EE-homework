package phattrienungdungvoi2ee.kiemtragiuaky.controller;

import phattrienungdungvoi2ee.kiemtragiuaky.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enroll")
public class EnrollController {

    @Autowired private EnrollmentService enrollmentService;

    @PostMapping("/{courseId}")
    public String enroll(@PathVariable int courseId,
                         Authentication authentication,
                         RedirectAttributes redirectAttributes) {

        String username;
        Object principal = authentication.getPrincipal();

        if (principal instanceof org.springframework.security.oauth2.core.user.OAuth2User oAuth2User) {
            username = oAuth2User.getAttribute("email");
        } else {
            // UserDetails thông thường
            username = authentication.getName();
        }

        // In ra log để kiểm tra
        System.out.println(">>> ENROLL username: " + username);
        System.out.println(">>> ENROLL courseId: " + courseId);

        String result = enrollmentService.enroll(username, courseId);
        System.out.println(">>> ENROLL result: " + result);

        if (result.equals("success")) {
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký học phần thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", result);
        }

        return "redirect:/home";
    }

    @GetMapping("/my-courses")
    public String myCourses(Authentication authentication, Model model) {
        String username;
        Object principal = authentication.getPrincipal();

        if (principal instanceof org.springframework.security.oauth2.core.user.OAuth2User oAuth2User) {
            username = oAuth2User.getAttribute("email");
        } else {
            username = authentication.getName();
        }

        model.addAttribute("enrollments", enrollmentService.getEnrollmentsByUsername(username));
        return "enroll/my-courses";
    }
}