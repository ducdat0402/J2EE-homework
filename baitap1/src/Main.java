import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Book> listBook = new ArrayList<>();
        Scanner x = new Scanner(System.in);

        String msg = """
                ╔════════════════════════════════════╗
                ║   CHƯƠNG TRÌNH QUẢN LÝ SÁCH        ║
                ╠════════════════════════════════════╣
                ║ 1. Thêm 1 cuốn sách                ║
                ║ 2. Xóa 1 cuốn sách                 ║
                ║ 3. Thay đổi sách                   ║
                ║ 4. Xuất thông tin                  ║
                ║ 5. Tìm sách Lập trình              ║
                ║ 6. Lấy sách tối đa theo giá        ║
                ║ 7. Tìm kiếm theo tác giả           ║
                ║ 0. Thoát                           ║
                ╚════════════════════════════════════╝
                Chọn chức năng: """;

        int chon = 0;
        do {
            System.out.print(msg);
            chon = x.nextInt();
            x.nextLine(); // Xóa bộ đệm

            switch (chon) {
                case 1 -> {
                    // Thêm 1 cuốn sách
                    System.out.println("\n=== THÊM SÁCH MỚI ===");
                    Book newBook = new Book();
                    newBook.input();
                    listBook.add(newBook);
                    System.out.println("✓ Đã thêm sách thành công!");
                }

                case 2 -> {
                    // Xóa 1 cuốn sách
                    System.out.print("\n=== XÓA SÁCH ===\nNhập mã sách cần xóa: ");
                    int bookId = x.nextInt();

                    Book find = listBook.stream()
                            .filter(p -> p.getId() == bookId)
                            .findFirst()
                            .orElse(null);

                    if (find != null) {
                        listBook.remove(find);
                        System.out.println("✓ Đã xóa sách thành công!");
                    } else {
                        System.out.println("✗ Không tìm thấy sách có mã " + bookId);
                    }
                }

                case 3 -> {
                    // Thay đổi thông tin sách
                    System.out.print("\n=== SỬA THÔNG TIN SÁCH ===\nNhập mã sách cần sửa: ");
                    int bookId = x.nextInt();
                    x.nextLine();

                    Book find = listBook.stream()
                            .filter(p -> p.getId() == bookId)
                            .findFirst()
                            .orElse(null);

                    if (find != null) {
                        System.out.println("Thông tin hiện tại:");
                        find.output();
                        System.out.println("\nNhập thông tin mới:");

                        System.out.print("Tên sách mới: ");
                        find.setTitle(x.nextLine());

                        System.out.print("Tác giả mới: ");
                        find.setAuthor(x.nextLine());

                        System.out.print("Giá mới: ");
                        find.setPrice(x.nextDouble());

                        System.out.println("✓ Đã cập nhật thành công!");
                    } else {
                        System.out.println("✗ Không tìm thấy sách!");
                    }
                }

                case 4 -> {
                    // Xuất danh sách
                    System.out.println("\n=== DANH SÁCH TẤT CẢ SÁCH ===");
                    if (listBook.isEmpty()) {
                        System.out.println("Danh sách trống!");
                    } else {
                        listBook.forEach(Book::output);
                    }
                }

                case 5 -> {
                    // Tìm sách có chứa "Lập trình"
                    System.out.println("\n=== TÌM SÁCH CHỨA 'LẬP TRÌNH' ===");
                    List<Book> list5 = listBook.stream()
                            .filter(u -> u.getTitle().toLowerCase().contains("lập trình"))
                            .toList();

                    if (list5.isEmpty()) {
                        System.out.println("Không tìm thấy sách nào!");
                    } else {
                        list5.forEach(Book::output);
                    }
                }

                case 6 -> {
                    // Lấy K cuốn sách có giá <= P
                    System.out.println("\n=== LẤY SÁCH THEO GIÁ ===");
                    System.out.print("Nhập số lượng K cần lấy: ");
                    int k = x.nextInt();
                    System.out.print("Nhập giá tối đa P: ");
                    double p = x.nextDouble();

                    List<Book> list6 = listBook.stream()
                            .filter(book -> book.getPrice() <= p)
                            .limit(k)
                            .toList();

                    if (list6.isEmpty()) {
                        System.out.println("Không có sách nào phù hợp!");
                    } else {
                        System.out.println("Tìm thấy " + list6.size() + " cuốn:");
                        list6.forEach(Book::output);
                    }
                }

                case 7 -> {
                    // Tìm sách theo danh sách tác giả
                    System.out.println("\n=== TÌM SÁCH THEO TÁC GIẢ ===");
                    System.out.print("Nhập số lượng tác giả: ");
                    int n = x.nextInt();
                    x.nextLine();

                    Set<String> authorSet = new HashSet<>();
                    for (int i = 0; i < n; i++) {
                        System.out.print("Nhập tên tác giả " + (i + 1) + ": ");
                        authorSet.add(x.nextLine().toLowerCase());
                    }

                    List<Book> list7 = listBook.stream()
                            .filter(book -> authorSet.contains(book.getAuthor().toLowerCase()))
                            .toList();

                    if (list7.isEmpty()) {
                        System.out.println("Không tìm thấy sách nào!");
                    } else {
                        System.out.println("Tìm thấy " + list7.size() + " cuốn:");
                        list7.forEach(Book::output);
                    }
                }

                case 0 -> {
                    System.out.println("\n👋 Cảm ơn bạn đã sử dụng chương trình!");
                }

                default -> {
                    System.out.println("✗ Lựa chọn không hợp lệ!");
                }
            }

            if (chon != 0) {
                System.out.println("\nNhấn Enter để tiếp tục...");
                x.nextLine();
            }

        } while (chon != 0);

        x.close();
    }
}