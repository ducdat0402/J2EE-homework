package phattrienungdungvoi2ee.kiemtragiuaky.service;


import phattrienungdungvoi2ee.kiemtragiuaky.dto.CartItem;
import phattrienungdungvoi2ee.kiemtragiuaky.model.*;
import phattrienungdungvoi2ee.kiemtragiuaky.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderDetailRepository orderDetailRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private CourseRepository courseRepository;

    // Tạo order từ cart
    public Order checkout(String username, List<CartItem> cartItems) {
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy student"));

        // Tạo order
        Order order = new Order();
        order.setStudent(student);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("CONFIRMED");

        // Tính tổng tiền
        long totalAmount = cartItems.stream()
                .mapToLong(CartItem::getSubtotal)
                .sum();
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        // Tạo order details
        List<OrderDetail> details = new ArrayList<>();
        for (CartItem item : cartItems) {
            Course course = courseRepository.findById(item.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy course"));

            OrderDetail detail = new OrderDetail();
            detail.setOrder(savedOrder);
            detail.setCourse(course);
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getPrice());
            details.add(detail);
        }

        orderDetailRepository.saveAll(details);
        savedOrder.setOrderDetails(details);

        return savedOrder;
    }

    // Lấy orders của student
    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.findByStudent_Username(username);
    }
}
