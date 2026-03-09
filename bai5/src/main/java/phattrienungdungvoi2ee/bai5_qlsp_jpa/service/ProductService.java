package phattrienungdungvoi2ee.bai5_qlsp_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import phattrienungdungvoi2ee.bai5_qlsp_jpa.model.Product;
import phattrienungdungvoi2ee.bai5_qlsp_jpa.repository.ProductRepository;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public void saveImage(Product product, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Path dirImages = Paths.get("static/images");
                if (!Files.exists(dirImages)) Files.createDirectories(dirImages);
                String newFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path pathUpload = dirImages.resolve(newFileName);
                Files.copy(imageFile.getInputStream(), pathUpload,
                        StandardCopyOption.REPLACE_EXISTING);
                product.setImage(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}