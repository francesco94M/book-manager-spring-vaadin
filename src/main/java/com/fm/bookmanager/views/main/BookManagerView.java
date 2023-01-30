package com.fm.bookmanager.views.main;

import static com.fm.bookmanager.utils.Constants.BOOK_MANAGER_PATH;

import javax.annotation.security.PermitAll;

import com.fm.bookmanager.service.BookService;
import com.fm.bookmanager.views.components.BookCrudComponent;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Book Manager")
@Route(value = BOOK_MANAGER_PATH+"view", layout = AppLayout.class)
@PermitAll
public class FindBookView extends Div {

    private VerticalLayout mainLayout;

    private final BookService bookService;

    public FindBookView(BookService bookService) {
        this.bookService = bookService;

        //addClassNames("appbook-manager-view");

        mainLayout = new VerticalLayout();
        mainLayout.setWidthFull();
        mainLayout.setHeightFull();
        initializeCrudComponent();
        add(mainLayout);

    }



    private void initializeCrudComponent()
    {
        BookCrudComponent crud = new BookCrudComponent(bookService);
        crud.setSizeFull();
        mainLayout.add(crud);
    }



}
