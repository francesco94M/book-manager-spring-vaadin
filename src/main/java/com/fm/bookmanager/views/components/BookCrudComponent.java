package com.fm.bookmanager.views.components;

import com.fm.bookmanager.entity.Book;
import com.fm.bookmanager.service.BookService;

import com.fm.bookmanager.utils.BookViewHelper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ErrorLevel;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;


public class BookCrudComponent extends Div {


    private Crud<Book> crud;

    private String EDIT_COLUMN = "vaadin-crud-edit-column";
    private final BookService service;


    public static final Validator<String> digitValidator = (s, valueContext) -> {
        ValidationResult result  = ValidationResult.ok();
        if(!NumberUtils.isParsable(s))
        {

            result  = ValidationResult.create(      "Field must be a number", ErrorLevel.ERROR);
        }
        return result;
    };
    public BookCrudComponent(BookService service) {
        this.service = service;
        crud = new Crud<>(Book.class, setupEditor());
        crud.setSizeFull();
        setupGrid();
        setupDataProvider();
        add(crud);
    }

    private CrudEditor<Book> setupEditor() {
        Component nameField = BookViewHelper.FIELD.NAME.getInputFieldSupplier().get();
        Component authorField = BookViewHelper.FIELD.AUTHOR.getInputFieldSupplier().get();
        Component pagesField = BookViewHelper.FIELD.PAGES.getInputFieldSupplier().get();
        Component eanCodeField = BookViewHelper.FIELD.EAN_CODE.getInputFieldSupplier().get();
        Component publicationDateField = BookViewHelper.FIELD.PUBLICATION_DATE.getInputFieldSupplier().get();

        Binder<Book> binder = new Binder<>(Book.class);
        binder.forField((HasValue<?,String>)nameField).asRequired().bind(Book::getName,
                Book::setName);
        binder.forField((HasValue<?,String>)authorField).asRequired().bind(Book::getAuthor,
                Book::setAuthor);
        binder.forField((HasValue<?,String>)pagesField).asRequired("Pages are required").withValidator(digitValidator).bind((book -> book.getPages()!=null ? book.getPages().toString():""), (Setter<Book, String>) (book, pagesString) -> book.setPages(Integer.parseInt(pagesString)));
        binder.forField((HasValue<?,String>)eanCodeField).withValidator((Validator<String>) (string, valueContext) -> {
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
        binder.forField((HasValue<?, LocalDate>)publicationDateField).asRequired().bind(Book::getPublicationDate,
                Book::setPublicationDate);



        FormLayout form = new FormLayout(nameField, authorField,pagesField,eanCodeField,publicationDateField);
        form.setColspan(nameField, 2);
        form.setColspan(authorField, 2);
        form.setColspan(pagesField, 2);
        form.setColspan(eanCodeField, 2);
        form.setColspan(publicationDateField, 2);

        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("30em", 2));

        return new BinderCrudEditor<>(binder, form);
    }

    private void setupGrid() {
        Grid<Book> grid = crud.getGrid();

        // Only show these columns (all columns shown by default):
        List<String> visibleColumns = Arrays.asList(BookViewHelper.FIELD.NAME.getValue(), BookViewHelper.FIELD.AUTHOR.getValue(),BookViewHelper.FIELD.PAGES.getValue(), BookViewHelper.FIELD.EAN_CODE.getValue(),BookViewHelper.FIELD.PUBLICATION_DATE.getValue(),EDIT_COLUMN);
        grid.getColumns().forEach(column -> {
            String key = column.getKey();
            if (!visibleColumns.contains(key)) {
                grid.removeColumn(column);
            }
        });

        grid.setColumnOrder(grid.getColumnByKey(BookViewHelper.FIELD.NAME.getValue()),
                grid.getColumnByKey(BookViewHelper.FIELD.AUTHOR.getValue()),grid.getColumnByKey(BookViewHelper.FIELD.PAGES.getValue()),grid.getColumnByKey(BookViewHelper.FIELD.EAN_CODE.getValue()),grid.getColumnByKey(BookViewHelper.FIELD.PUBLICATION_DATE.getValue()), grid.getColumnByKey(EDIT_COLUMN));
    }

    private void setupDataProvider() {
        BookCrudDataProvider dataProvider = new BookCrudDataProvider(service);
        crud.setDataProvider(dataProvider);
        crud.addDeleteListener(
                deleteEvent -> dataProvider.delete(deleteEvent.getItem()));
        crud.addSaveListener(
                saveEvent -> dataProvider.persist(saveEvent.getItem()));
    }

}
