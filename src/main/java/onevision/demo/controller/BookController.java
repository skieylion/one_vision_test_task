package onevision.demo.controller;

import lombok.AllArgsConstructor;
import onevision.demo.domain.Book;
import onevision.demo.service.BookService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @PostMapping("/books")
    public void createBook(@RequestBody Book book) {
        bookService.createBook(book);
    }

    @GetMapping("grouped-authors/books")
    public Map<String, List<Book>> getBooksGroupByAuthors() {
        return bookService.getBooksGroupByAuthors();
    }

    @GetMapping("/sorted-symbol-matches/authors")
    public List<Map<String, Integer>> getCountSymbolList(@RequestParam(value = "symbol") char symbol) {
        return bookService.getCountSymbolList(symbol);
    }

}
