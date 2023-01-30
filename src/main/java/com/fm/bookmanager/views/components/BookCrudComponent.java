package com.fm.bookmanager.views.main;

import com.fm.bookmanager.entity.Book;
import com.fm.bookmanager.service.BookService;

import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ErrorLevel;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;


public class BookCrudView extends Div {


    private Crud<Book> crud;

    private String NAME = "name";
    private String AUTHOR = "author";

    private String PAGES = "pages";

    private String EAN_CODE = "eanCode";

    private String PUBLICATION_DATE = "publicationDate";
    private String EDIT_COLUMN = "vaadin-crud-edit-column";
    private final BookService service;

    private Validator<String> digitValidator = (s, valueContext) -> {
        ValidationResult result  = ValidationResult.ok();
        if(!NumberUtils.isParsable(s))
        {

            result  = ValidationResult.create(      "Field must be a number", ErrorLevel.ERROR);
        }
        return result;
    };

    public BookCrudView(BookService service) {
        this.service = service;
        crud = new Crud<>(Book.class, createEditor());

        crud.setSizeFull();
        setupGrid();
        setupDataProvider();

        add(crud);
    }

    private CrudEditor<Book> createEditor() {
        TextField name = new TextField("First name");
        TextField author = new TextField("Author");
        TextField pages = new TextField("Pages");
        TextField eanCode = new TextField("EAN");
        DatePicker publishingDate = new DatePicker("Published on");


        FormLayout form = new FormLayout(name, author,pages,eanCode,publishingDate);
        form.setColspan(name, 2);
        form.setColspan(author, 2);
        form.setColspan(pages, 2);
        form.setColspan(eanCode, 2);
        form.setColspan(publishingDate, 2);

        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("30em", 2));

        Binder<Book> binder = new Binder<>(Book.class);
        binder.forField(name).asRequired().bind(Book::getName,
                Book::setName);
        binder.forField(author).asRequired().bind(Book::getAuthor,
                Book::setAuthor);
        binder.forField(pages).asRequired("Pages are required").withValidator(digitValidator).bind((book -> book.getPages()!=null ? book.getPages().toString():""), (Setter<Book, String>) (book, pagesString) -> book.setPages(Integer.parseInt(pagesString)));
        binder.forField(eanCode).withValidator((Validator<String>) (string, valueContext) -> {
            ValidationResult result;
            if (StringUtils.isNotBlank(string) && string.length()!=9)
            {
                result = ValidationResult.create("Ean code must have 9 digits", ErrorLevel.ERROR);
            }
            else
            {
                result = digitValidator.apply(string, valueContext);
            }
            return result;
        })
        .asRequired().bind(Book::getEanCode,
                Book::setEanCode);
        binder.forField(publishingDate).asRequired().bind(Book::getPublicationDate,
                Book::setPublicationDate);


        return new BinderCrudEditor<>(binder, form);
    }

    private void setupGrid() {
        Grid<Book> grid = crud.getGrid();

        // Only show these columns (all columns shown by default):
        List<String> visibleColumns = Arrays.asList(NAME, AUTHOR,PAGES, EAN_CODE,PUBLICATION_DATE,EDIT_COLUMN);
        grid.getColumns().forEach(column -> {
            String key = column.getKey();
            if (!visibleColumns.contains(key)) {
                grid.removeColumn(column);
            }
        });

        // Reorder the columns (alphabetical by default)
        grid.setColumnOrder(grid.getColumnByKey(NAME),
                grid.getColumnByKey(AUTHOR),grid.getColumnByKey(PAGES),grid.getColumnByKey(EAN_CODE),grid.getColumnByKey(PUBLICATION_DATE), grid.getColumnByKey(EDIT_COLUMN));
    }

    private void setupDataProvider() {
        BookDataProvider dataProvider = new BookDataProvider(service);
        crud.setDataProvider(dataProvider);
        crud.addDeleteListener(
                deleteEvent -> dataProvider.delete(deleteEvent.getItem()));
        crud.addSaveListener(
                saveEvent -> dataProvider.persist(saveEvent.getItem()));
    }

}
