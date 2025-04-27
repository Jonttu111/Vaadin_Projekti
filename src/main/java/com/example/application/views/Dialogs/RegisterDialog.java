package com.example.application.views.Dialogs;

import com.example.application.entitys.User;
import com.example.application.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class RegisterDialog extends Dialog {

    public RegisterDialog(UserService userService) {
        User user = new User();
        VerticalLayout layout = new VerticalLayout();
        FormLayout formLayout = new FormLayout();
        Binder<User> binder = new Binder<>(User.class);


        //Header
        formLayout.add(new H2("Register"));
        // Username field
        com.vaadin.flow.component.textfield.TextField usernameField = new TextField("Username");
        usernameField.setRequired(true);
        usernameField.setPlaceholder("Enter username");

        // Password field
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setRequired(true);
        passwordField.setPlaceholder("Enter password");
        formLayout.add(usernameField, passwordField);

        //Confirm password field
        PasswordField confirmPasswordField = new PasswordField("Confirm Password");
        confirmPasswordField.setRequired(true);
        confirmPasswordField.setPlaceholder("Confirm password");
        formLayout.add(confirmPasswordField);

        //Binder

        binder.forField(usernameField).asRequired("Username is required")
                .withValidator(username -> username.length() >= 3 && username.length() <= 20, "Username must be between 3 and 20 characters")
                .bind(User::getUsername, User::setUsername);
        binder.forField(passwordField).asRequired("Password if required")
                .withValidator(password -> password.length() >= 6 && password.length() <= 40, "Password must be between 6 and 40 characters")
                .bind(user1 -> "", (user1, value) -> {});
        binder.forField(confirmPasswordField).withValidator((value, context ) -> {
            if(!value.equals(passwordField.getValue())){
                return ValidationResult.error("Passwords do not match");
            }
            return ValidationResult.ok();
        }).bind(user1 -> "", (user1, value) -> {

        });


        //Buttons
        Button btn_Create = new Button("Create");
        Button btn_Cancel = new Button("Cancel");
        formLayout.add(btn_Create, btn_Cancel);

        btn_Create.addClickListener(event -> {
            if(binder.validate().isOk()){
                System.out.println(usernameField.getValue());
                if (userService.isUserRegistered(usernameField.getValue())) {
                    Notification.show("User already exists");
                }
                else{
                    Notification.show("User created");
                    userService.registerUser(usernameField.getValue(), passwordField.getValue());
                    userService.getAllUsers().forEach(System.out::println);

                    close();
                }
            }
        });

        btn_Cancel.addClickListener(event -> close());

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        layout.add(formLayout);
        add(layout);
    }


}
