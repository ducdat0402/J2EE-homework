package phattrienungdungvoi2ee.bai5_qlsp_jpa.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phattrienungdungvoi2ee.bai5_qlsp_jpa.model.*;
import phattrienungdungvoi2ee.bai5_qlsp_jpa.service.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;

    // Danh sách
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/list";
    }

    // Form thêm
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/add";
    }

    // Lưu sản phẩm mới
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult result,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "product/add";
        }
        productService.saveImage(product, imageFile);
        productService.saveProduct(product);
        return "redirect:/products";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) return "redirect:/products";
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/edit";
    }

    // Cập nhật
    @PostMapping("/update")
    public String updateProduct(@Valid @ModelAttribute("product") Product product,
                                BindingResult result,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "product/edit";
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            productService.saveImage(product, imageFile);
        }
        productService.saveProduct(product);
        return "redirect:/products";
    }

    // Xoá
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}