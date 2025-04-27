package com.example.application.views.welcome;

import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Welcome")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.HOME_SOLID)
@AnonymousAllowed
public class WelcomeView extends VerticalLayout {

    private final AuthenticatedUser authenticatedUser;

    public WelcomeView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        var user = authenticatedUser.get();
        H2 title = new H2("V");
        if(user.isPresent()){
            title.setText("Welcome " + user.get().getUsername());
            add(title);
        }
        else{
            title.setText("Welcome Guest");
            add(title);
        }
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H2 header = new H2("This place intentionally left empty");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
