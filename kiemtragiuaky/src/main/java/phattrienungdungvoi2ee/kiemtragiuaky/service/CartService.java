package phattrienungdungvoi2ee.kiemtragiuaky.service;

import phattrienungdungvoi2ee.kiemtragiuaky.dto.CartItem;
import phattrienungdungvoi2ee.kiemtragiuaky.model.Course;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import java.util.*;

@Service
@SessionScope // Lưu vào session
public class CartService {

    private final Map<Integer, CartItem> cartItems = new LinkedHashMap<>();

    // Thêm vào giỏ
    public void addToCart(Course course, int quantity) {
        if (cartItems.containsKey(course.getId())) {
            // Đã có → tăng số lượng
            CartItem item = cartItems.get(course.getId());
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            // Chưa có → thêm mới
            CartItem item = new CartItem(
                    course.getId(),
                    course.getName(),
                    course.getCredits(), // dùng credits làm "giá" demo
                    quantity
            );
            cartItems.put(course.getId(), item);
        }
    }

    // Lấy tất cả items
    public List<CartItem> getItems() {
        return new ArrayList<>(cartItems.values());
    }

    // Tổng tiền
    public long getTotalAmount() {
        return cartItems.values().stream()
                .mapToLong(CartItem::getSubtotal)
                .sum();
    }

    // Tổng số lượng
    public int getTotalQuantity() {
        return cartItems.values().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    // Xoá 1 item
    public void removeItem(int courseId) {
        cartItems.remove(courseId);
    }

    // Cập nhật số lượng
    public void updateQuantity(int courseId, int quantity) {
        if (cartItems.containsKey(courseId)) {
            if (quantity <= 0) {
                cartItems.remove(courseId);
            } else {
                cartItems.get(courseId).setQuantity(quantity);
            }
        }
    }

    // Xoá giỏ hàng
    public void clearCart() {
        cartItems.clear();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}