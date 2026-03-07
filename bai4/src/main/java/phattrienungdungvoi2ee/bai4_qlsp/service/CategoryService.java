package phattrienungdungvoi2ee.bai4_qlsp.service;

import org.springframework.stereotype.Service;
import phattrienungdungvoi2ee.bai4_qlsp.model.Category;
import java.util.*;

@Service
public class CategoryService {
    List<Category> listCategory = new ArrayList<>(Arrays.asList(
            new Category(1, "Điện thoại"),
            new Category(2, "Laptop")
    ));

    public List<Category> getAll() { return listCategory; }

    public Category get(int id) {
        return listCategory.stream()
                .filter(c -> c.getId() == id)
                .findFirst().orElse(null);
    }
}