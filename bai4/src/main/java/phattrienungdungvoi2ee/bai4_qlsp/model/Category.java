package phattrienungdungvoi2ee.bai4_qlsp.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Integer id;

    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
}