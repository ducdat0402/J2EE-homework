package phattrienungdungvoi2ee.kiemtragiuaky.repository;

import phattrienungdungvoi2ee.kiemtragiuaky.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByStudent_Username(String username);
}
