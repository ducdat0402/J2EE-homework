package phattrienungdungvoi2ee.bai5_qlsp_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phattrienungdungvoi2ee.bai5_qlsp_jpa.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}