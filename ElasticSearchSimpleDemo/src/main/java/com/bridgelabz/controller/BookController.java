package com.bridgelabz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.dao.BookDao;
import com.bridgelabz.model.Book;

import java.util.Map;
@RestController
@RequestMapping("/books")
public class BookController {

    private BookDao bookDao;

    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }
    @GetMapping("/{id}")
    public Map<String, Object> getBookById(@PathVariable String id){
      return bookDao.getBookById(id);
    }
    @PostMapping
    public Book insertBook(@RequestBody Book book) throws Exception {
      return bookDao.insertBook(book);
    }
}
