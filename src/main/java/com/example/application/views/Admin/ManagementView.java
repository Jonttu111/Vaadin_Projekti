package com.example.application.views.Admin;

import com.example.application.entitys.Role;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@Route("management")
@PageTitle("Management")
@RolesAllowed("ADMIN")
@Menu(order = 3, icon = LineAwesomeIconUrl.ADN)
public class ManagementView extends VerticalLayout {

    public ManagementView() {
        H2 header = new H2("This is admin page and only admin can see it");
        add(header);
    }
}
