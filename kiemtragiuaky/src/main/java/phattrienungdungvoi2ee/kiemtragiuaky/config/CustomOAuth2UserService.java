package phattrienungdungvoi2ee.kiemtragiuaky.config;


import phattrienungdungvoi2ee.kiemtragiuaky.model.Role;
import phattrienungdungvoi2ee.kiemtragiuaky.model.Student;
import phattrienungdungvoi2ee.kiemtragiuaky.repository.RoleRepository;
import phattrienungdungvoi2ee.kiemtragiuaky.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired private StudentRepository studentRepository;
    @Autowired private RoleRepository roleRepository;

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        // Load user info từ Google
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");

        // Tìm hoặc tạo student trong DB
        Student student = studentRepository.findByUsername(email)
                .orElseGet(() -> {
                    Student newStudent = new Student();
                    newStudent.setUsername(email);
                    newStudent.setPassword("");
                    newStudent.setEmail(email);

                    Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                            .orElseThrow(() -> new RuntimeException("Không tìm thấy ROLE_STUDENT"));

                    newStudent.setRoles(new HashSet<>(Set.of(studentRole)));
                    return studentRepository.save(newStudent);
                });

        // Build authorities từ roles trong DB
        Set<SimpleGrantedAuthority> authorities = student.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // Trả về OAuth2User với đúng authorities từ DB
        return new DefaultOAuth2User(
                authorities,
                oAuth2User.getAttributes(),
                "email"  // attribute dùng làm getName()
        );
    }
}