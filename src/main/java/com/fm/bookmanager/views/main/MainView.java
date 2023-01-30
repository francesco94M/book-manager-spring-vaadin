package com.fm.bookmanager.views.main;

import com.fm.bookmanager.entity.User;
import com.fm.bookmanager.service.UserService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Login to Book Manager")
@Route("")
public class MainView extends VerticalLayout implements BeforeEnterObserver
{


    private final UserService userService;
    public MainView(UserService userService) {
        this.userService = userService;



        VerticalLayout layout = new VerticalLayout();
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        add(layout);

        com.vaadin.flow.component.textfield.TextField username = new TextField("Username");
        PasswordField passwordField  = new PasswordField("Password");
        Button submitButton = new Button("Login");
        Text messageField = new Text("");
        FormLayout formLayout = new FormLayout(username,passwordField,submitButton);
        formLayout.setWidth("50%");
        formLayout.setColspan(submitButton,2);
        submitButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            User loggedUser = userService.findByUsername(username.getValue()).orElse(null);
            if(loggedUser==null)
            {
                showErrorNotification("User with username '"+username.getValue()+"' not exists");
            }
            else if(!loggedUser.getPassword().equals(passwordField.getValue()))
            {

                showErrorNotification("Password '"+passwordField.getValue()+"' is incorrect");
            }
            else
            {
                UI.getCurrent().navigate(BookManagerView.BASE_PATH);
            }

        });


        layout.add(new Header(new Text("Login Page")));
        layout.add(formLayout);
        layout.add(messageField);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent)
    {
        //beforeEnterEvent.forwardTo(BookManagerView.BASE_PATH);
    }

    private void showErrorNotification(String message)
    {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setPosition(Notification.Position.TOP_CENTER);
        Div text = new Div(new Text(message));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });
        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();

    }
}