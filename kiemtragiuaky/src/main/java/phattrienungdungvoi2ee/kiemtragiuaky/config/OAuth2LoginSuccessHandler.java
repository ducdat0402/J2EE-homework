package phattrienungdungvoi2ee.kiemtragiuaky.config;


import phattrienungdungvoi2ee.kiemtragiuaky.model.Role;
import phattrienungdungvoi2ee.kiemtragiuaky.model.Student;
import phattrienungdungvoi2ee.kiemtragiuaky.repository.RoleRepository;
import phattrienungdungvoi2ee.kiemtragiuaky.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {
        response.sendRedirect("/home");
    }
}