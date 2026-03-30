package phattrienungdungvoi2ee.kiemtragiuaky.controller;


import phattrienungdungvoi2ee.kiemtragiuaky.model.Course;
import phattrienungdungvoi2ee.kiemtragiuaky.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired private CartService cartService;
    @Autowired private CourseService courseService;
    @Autowired private OrderService orderService;

    // Xem giỏ hàng
    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems",    cartService.getItems());
        model.addAttribute("totalAmount",  cartService.getTotalAmount());
        model.addAttribute("totalQty",     cartService.getTotalQuantity());
        return "cart/cart";
    }

    // Thêm vào giỏ
    @PostMapping("/add/{courseId}")
    public String addToCart(@PathVariable int courseId,
                            @RequestParam(defaultValue = "1") int quantity,
                            RedirectAttributes ra) {
        Course course = courseService.getById(courseId);
        if (course == null) {
            ra.addFlashAttribute("errorMessage", "Không tìm thấy học phần!");
            return "redirect:/home";
        }
        cartService.addToCart(course, quantity);
        ra.addFlashAttribute("successMessage",
                "Đã thêm \"" + course.getName() + "\" vào giỏ hàng!");
        return "redirect:/home";
    }

    // Xoá 1 item
    @PostMapping("/remove/{courseId}")
    public String removeItem(@PathVariable int courseId) {
        cartService.removeItem(courseId);
        return "redirect:/cart";
    }

    // Cập nhật số lượng
    @PostMapping("/update/{courseId}")
    public String updateQuantity(@PathVariable int courseId,
                                 @RequestParam int quantity) {
        cartService.updateQuantity(courseId, quantity);
        return "redirect:/cart";
    }

    // Xoá toàn bộ giỏ
    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }

    // Đặt hàng
    @PostMapping("/checkout")
    public String checkout(Authentication authentication,
                           RedirectAttributes ra) {
        if (cartService.isEmpty()) {
            ra.addFlashAttribute("errorMessage", "Giỏ hàng trống!");
            return "redirect:/cart";
        }

        // Lấy username
        String username;
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2User oAuth2User) {
            username = oAuth2User.getAttribute("email");
        } else {
            username = authentication.getName();
        }

        orderService.checkout(username, cartService.getItems());
        cartService.clearCart();

        ra.addFlashAttribute("successMessage", "Đặt hàng thành công! 🎉");
        return "redirect:/cart/orders";
    }

    // Xem lịch sử đơn hàng
    @GetMapping("/orders")
    public String viewOrders(Authentication authentication, Model model) {
        String username;
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2User oAuth2User) {
            username = oAuth2User.getAttribute("email");
        } else {
            username = authentication.getName();
        }

        model.addAttribute("orders", orderService.getOrdersByUsername(username));
        return "cart/orders";
    }
}