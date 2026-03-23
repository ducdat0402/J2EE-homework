package phattrienungdungvoi2ee.kiemtragiuaky.service;


import phattrienungdungvoi2ee.kiemtragiuaky.model.Category;
import phattrienungdungvoi2ee.kiemtragiuaky.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }
}