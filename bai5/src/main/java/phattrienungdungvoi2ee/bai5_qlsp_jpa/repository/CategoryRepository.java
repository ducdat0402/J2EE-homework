package phattrienungdungvoi2ee.bai5_qlsp_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phattrienungdungvoi2ee.bai5_qlsp_jpa.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}