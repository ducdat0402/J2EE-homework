package phattrienungdungvoi2ee.kiemtragiuaky.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private int courseId;
    private String courseName;
    private long price;
    private int quantity;

    public long getSubtotal() {
        return price * quantity;
    }
}
