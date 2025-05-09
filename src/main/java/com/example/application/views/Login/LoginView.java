package com.example.application.views.Login;


import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.web.servlet.view.RedirectView;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;

    public LoginView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        try{

            setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

            LoginI18n i18n = LoginI18n.createDefault();
            i18n.setHeader(new LoginI18n.Header());
            i18n.getHeader().setTitle("My App");
            i18n.getHeader().setDescription("Login using user/user or admin/admin");
            i18n.setAdditionalInformation(null);
            setI18n(i18n);

            setForgotPasswordButtonVisible(false);
            setOpened(true);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            // Already logged in
            setOpened(false);
            event.forwardTo("");
        }

        setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}

