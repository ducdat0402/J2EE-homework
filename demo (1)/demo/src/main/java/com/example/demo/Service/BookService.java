package com.example.demo.Service;

import com.example.demo.Model.Book;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();

    // Constructor - khởi tạo 5 cuốn sách mẫu
    public BookService() {
        books.add(new Book(1, "Dế Mèn Phiêu Lưu Ký", "Tô Hoài"));
        books.add(new Book(2, "Số Đỏ", "Vũ Trọng Phụng"));
        books.add(new Book(3, "Tắt Đèn", "Ngô Tất Tố"));
        books.add(new Book(4, "Chí Phèo", "Nam Cao"));
        books.add(new Book(5, "Lão Hạc", "Nam Cao"));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void updateBook(int id, Book updatedBook) {
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                });
    }

    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}