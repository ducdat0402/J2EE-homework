package phattrienungdungvoi2ee.kiemtragiuaky.repository;


import phattrienungdungvoi2ee.kiemtragiuaky.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
