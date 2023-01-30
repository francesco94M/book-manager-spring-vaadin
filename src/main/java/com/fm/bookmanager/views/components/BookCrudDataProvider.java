package com.fm.bookmanager.views.main;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.fm.bookmanager.entity.Book;
import com.fm.bookmanager.service.BookService;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import static java.util.Comparator.naturalOrder;

import org.springframework.beans.factory.annotation.Autowired;


// Person data provider
public class BookDataProvider
        extends AbstractBackEndDataProvider<Book, CrudFilter> {

    private List<Book> allBooks;
    private Map<Long,Book> allBooksById;

    private final BookService service;
    private Consumer<Long> sizeChangeListener;

    public BookDataProvider(BookService service)
    {
        this.service = service;
    }

    @Override
    protected Stream<Book> fetchFromBackEnd(Query<Book, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();
        refreshLocalBooksData();
        Stream<Book> stream = allBooks.stream();

        if (query.getFilter().isPresent()) {
            stream = stream.filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<Book, CrudFilter> query) {
        // For RDBMS just execute a SELECT COUNT(*) ... WHERE query
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }

    void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    private static Predicate<Book> predicate(CrudFilter filter) {
        // For RDBMS just generate a WHERE clause
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<Book>) Book -> {
                    try {
                        Object value = valueOf(constraint.getKey(), Book);
                        return value != null && value.toString().toLowerCase()
                                .contains(constraint.getValue().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }).reduce(Predicate::and).orElse(e -> true);
    }

    private static Comparator<Book> comparator(CrudFilter filter) {
        // For RDBMS just generate an ORDER BY clause
        return filter.getSortOrders().entrySet().stream().map(sortClause -> {
            try {
                Comparator<Book> comparator = Comparator.comparing(
                        Book -> (Comparable) valueOf(sortClause.getKey(),
                                Book));

                if (sortClause.getValue() == SortDirection.DESCENDING) {
                    comparator = comparator.reversed();
                }

                return comparator;

            } catch (Exception ex) {
                return (Comparator<Book>) (o1, o2) -> 0;
            }
        }).reduce(Comparator::thenComparing).orElse((o1, o2) -> 0);
    }

    private static Object valueOf(String fieldName, Book Book) {
        try {
            Field field = Book.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(Book);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    void persist(Book item) {
        if (item.getId() == null) {

            item.setId( allBooks.stream().map(Book::getId).max(naturalOrder())
                    .orElse(0L) + 1L);
        }
        //final Optional<Book> existingItem = find(item.getId());
        service.update(item);
        refreshLocalBooksData();
    }

    Optional<Book> find(Long id) {

        return Optional.ofNullable(allBooksById.get(id));
    }

    void delete(Book item) {
        service.delete(item.getId());
        refreshLocalBooksData();
    }

    private void refreshLocalBooksData()
    {
        allBooks = service.list();
        allBooksById = new HashMap<>();
        allBooks.stream().forEach( book ->
        {
            allBooksById.put(book.getId(),book);
        });
    }
}
