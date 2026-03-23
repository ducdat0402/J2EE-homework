package phattrienungdungvoi2ee.kiemtragiuaky.service;


import phattrienungdungvoi2ee.kiemtragiuaky.model.Student;
import phattrienungdungvoi2ee.kiemtragiuaky.model.Role;
import phattrienungdungvoi2ee.kiemtragiuaky.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy user: " + username));

        Set<SimpleGrantedAuthority> authorities = student.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new User(
                student.getUsername(),
                student.getPassword(),
                authorities
        );
    }
}
