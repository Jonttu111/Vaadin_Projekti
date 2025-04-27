package com.example.application.views;

import com.example.application.security.AuthenticatedUser;
import com.example.application.service.UserService;
import com.example.application.views.Dialogs.LoginDialog;
import com.example.application.views.Dialogs.RegisterDialog;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoUtility;

import com.vaadin.flow.component.dialog.Dialog;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;

import static org.atmosphere.util.IOUtils.close;

/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {

    private H1 viewTitle;
    private final UserService userService;
    private final AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(UserService userService, AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.userService = userService;
        this.accessChecker = accessChecker;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Span appName = new Span("Vaadin_Projekti");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();
        menuEntries.forEach(entry -> {
            if (entry.icon() != null) {
                nav.addItem(new SideNavItem(entry.title(), entry.path(), new SvgIcon(entry.icon())));
            } else {
                nav.addItem(new SideNavItem(entry.title(), entry.path()));
            }
        });

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        if(authenticatedUser.get().isPresent()){
            Button btn_logOut = new Button("Log out");
            btn_logOut.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            btn_logOut.addClickListener(event -> {
                authenticatedUser.logout();
            });
            layout.add(btn_logOut);
        }
        else{
            Button btn_logIn = new Button("Log in");
            Button btn_register = new Button("Register");

            btn_logIn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            btn_register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            btn_logIn.addClickListener(event -> {
                // dialog = new LoginDialog(userService, authenticatedUser);
                // dialog.open();
                getUI().ifPresent(ui -> ui.navigate("login"));
            });

            btn_register.addClickListener(event -> {
                Dialog dialog = new RegisterDialog(userService);
                dialog.open();
            });
            layout.add(btn_logIn, btn_register);

        }
        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }
}
