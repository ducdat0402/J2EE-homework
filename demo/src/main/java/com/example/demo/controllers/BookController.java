package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/books")
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("totalBooks", bookService.getAllBooks().size());
        return "books";
    }

    // Hiển thị form thêm sách mới
    @GetMapping("/books/new")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("action", "add");
        return "book-form";
    }

    // Xử lý thêm sách mới
    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        bookService.addBook(book);
        redirectAttributes.addFlashAttribute("message", "Thêm sách thành công!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/books";
    }

    // Hiển thị form sửa sách
    @GetMapping("/books/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy sách!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        model.addAttribute("action", "edit");
        return "book-form";
    }

    // Xử lý cập nhật sách
    @PostMapping("/books/update/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        book.setId(id);
        boolean updated = bookService.updateBook(book);
        if (updated) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật sách thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy sách để cập nhật!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/books";
    }

    // Xử lý xóa sách
    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable int id, RedirectAttributes redirectAttributes) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("message", "Xóa sách thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy sách để xóa!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/books";
    }

    // Xem chi tiết sách
    @GetMapping("/books/view/{id}")
    public String viewBook(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy sách!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "book-detail";
    }
}