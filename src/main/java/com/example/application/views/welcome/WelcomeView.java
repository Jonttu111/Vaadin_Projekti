package com.example.application.views.welcome;

import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import com.vaadin.flow.component.button.Button;
@CssImport("WelcomeView.css")
@PageTitle("Welcome")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.HOME_SOLID)
@AnonymousAllowed
public class WelcomeView extends VerticalLayout {

    private final AuthenticatedUser authenticatedUser;

    public WelcomeView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        var user = authenticatedUser.get();

        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");

        H1 title = new H1();
        title.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        if(user.isPresent()){
            title.setText("Welcome " + user.get().getUsername());
            add(title);
        }
        else{
            title.setText("Welcome Guest");
            add(title);
        }
        Button btn_example = new Button("Button component which includes custom CSS");
        btn_example.addClassName("custom");

        add(title, img, new Paragraph("Everyone can see this page"), btn_example);
        getStyle().set("background-color", "hotpink");
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
