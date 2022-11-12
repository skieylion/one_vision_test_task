package onevision.demo.service;

import lombok.AllArgsConstructor;
import onevision.demo.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private final JdbcTemplate jdbcTemplate;

    public List<Book> getBooks() {
        return jdbcTemplate.query(
                "select * from book order by title desc",
                (resultSet, rowNum) -> new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("description")
                )
        );
    }

    public void createBook(Book book) {
        jdbcTemplate.update(
                "insert into book(id,title,author,description) values (NEXTVAL('BOOK_SEQ'),?,?,?)",
                book.getTitle(),
                book.getAuthor(),
                book.getDescription()
        );
    }

    public Map<String, List<Book>> getBooksGroupByAuthors() {
        Map<String, List<Book>> result = new HashMap<>();
        for (Book book : getBooks()) {
            String key = book.getAuthor();
            if (result.containsKey(key)) {
                result.get(key).add(book);
                continue;
            }
            List<Book> bookList = new ArrayList<>();
            bookList.add(book);
            result.put(key, bookList);
        }
        return result;
    }

    public List<Map<String, Integer>> getCountSymbolList(char _symbol) {
        final String symbol = String.valueOf(_symbol).toLowerCase();
        List<Map<String, Integer>> result = new ArrayList<>();
        getBooksGroupByAuthors().forEach((author, books) -> {
            result.add(books.stream()
                    .map(book -> StringUtils.countOccurrencesOf(book.getTitle().toLowerCase(), symbol))
                    .reduce(Integer::sum)
                    .map(v -> Map.of(author, v))
                    .orElse(Map.of(author, 0))
            );
        });
        return result.stream()
                .sorted(Comparator.comparingInt(v -> -v.values().stream().findFirst().orElse(0)))
                .filter(map -> map.values().stream().findFirst().orElse(0) > 0)
                .limit(10)
                .collect(Collectors.toList());
    }

}
