package phattrienungdungvoi2ee.kiemtragiuaky.controller;


import phattrienungdungvoi2ee.kiemtragiuaky.dto.RegisterDTO;
import phattrienungdungvoi2ee.kiemtragiuaky.model.Role;
import phattrienungdungvoi2ee.kiemtragiuaky.model.Student;
import phattrienungdungvoi2ee.kiemtragiuaky.repository.RoleRepository;
import phattrienungdungvoi2ee.kiemtragiuaky.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RegisterController {

    @Autowired private StudentRepository studentRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    // Hiện form đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDTO") RegisterDTO dto,
                           BindingResult result,
                           Model model) {
        // Kiểm tra validation
        if (result.hasErrors()) {
            return "register";
        }

        // Kiểm tra username đã tồn tại chưa
        if (studentRepository.findByUsername(dto.getUsername()).isPresent()) {
            model.addAttribute("errorMessage", "Username đã tồn tại!");
            return "register";
        }

        // Tạo student mới
        Student student = new Student();
        student.setUsername(dto.getUsername());
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setEmail(dto.getEmail());

        // Gán quyền ROLE_STUDENT mặc định
        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role STUDENT"));

        Set<Role> roles = new HashSet<>();
        roles.add(studentRole);
        student.setRoles(roles);

        studentRepository.save(student);

        return "redirect:/login?registered";
    }
}