package com.example.application.views.Dialogs;

import com.example.application.security.AuthenticatedUser;
import com.example.application.service.UserService;

import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.dialog.Dialog;

import com.vaadin.flow.component.login.LoginForm;

import com.vaadin.flow.component.orderedlayout.FlexComponent;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
public class LoginDialog extends Dialog {

    // Yritin kovasti ja luovutin tämän kanssa. Jätin silti koska haluaisin saada tämän toimimaan.
    public LoginDialog(UserService userService, AuthenticatedUser authenticatedUser) {

        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        LoginForm loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(false);

        loginForm.addLoginListener(e -> {
            String username = e.getUsername();
            String password = e.getPassword();

            // Yhdistetään käyttäjätunnus ja salasana Spring Securityn kanssa
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            try {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // Ohjaa eteenpäin kirjautumisen jälkeen
                getUI().ifPresent(ui -> ui.navigate(""));
            } catch (Exception ex) {
                // Virheilmoitus, jos autentikointi epäonnistuu
                loginForm.setError(true);
            }
        });

        Button btn_Cancel = new Button("Cancel");
        btn_Cancel.setWidth("100%");
        btn_Cancel.addClickListener(event -> close());

        VerticalLayout layout1 = new VerticalLayout(loginForm, btn_Cancel);
        layout1.setAlignItems(FlexComponent.Alignment.CENTER);
        add(layout1);
    }
}
