package com.example.demo.service;

import com.example.demo.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();
    private AtomicInteger idGenerator = new AtomicInteger(2028);

    // Constructor khởi tạo dữ liệu mẫu
    public BookService() {
        books.add(new Book(2025, "J2EE Programming", "Huy Cuong"));
        books.add(new Book(2026, "Spring Boot in Action", "Nguyen Van A"));
        books.add(new Book(2027, "Thymeleaf Guide", "Tran Thi B"));
    }

    // Lấy tất cả sách
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    // Lấy sách theo ID
    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Thêm sách mới
    public Book addBook(Book book) {
        book.setId(idGenerator.getAndIncrement());
        books.add(book);
        return book;
    }

    // Cập nhật sách
    public boolean updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == updatedBook.getId()) {
                books.set(i, updatedBook);
                return true;
            }
        }
        return false;
    }

    // Xóa sách
    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }

    // Tìm kiếm sách theo tên
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBooks();
        }

        String lowerKeyword = keyword.toLowerCase();
        List<Book> results = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerKeyword) ||
                    book.getAuthor().toLowerCase().contains(lowerKeyword)) {
                results.add(book);
            }
        }

        return results;
    }
}