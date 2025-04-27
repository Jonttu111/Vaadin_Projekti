package com.example.application.views.MeasurementResults;

import com.example.application.entitys.Measurement;
import com.example.application.entitys.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.service.MeasurementService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.Optional;

@PageTitle("Results")
@Route(value = "results", layout = MainLayout.class)
@Menu(order = 2, icon = "la la-chart-pie")
@RolesAllowed( "USER")
public class MeasurementResults extends Div {
    private final MeasurementService measurementService;
    private final AuthenticatedUser authenticatedUser;

    public MeasurementResults(MeasurementService measurementService, AuthenticatedUser authenticatedUser) {
        this.measurementService = measurementService;
        this.authenticatedUser = authenticatedUser;

        Optional<User> username = authenticatedUser.get();

        Grid<Measurement> grid = new Grid<>(Measurement.class, false);
        grid.addColumn(Measurement::getSystolicPressure).setHeader("Systolic Pressure");
        grid.addColumn(Measurement::getDiastolicPressure).setHeader("Diastolic Pressure");
        grid.addColumn(Measurement::getHeartRate).setHeader("Heart Rate");
        grid.addColumn(Measurement::getTimestamp).setHeader("Timestamp");

        grid.addComponentColumn(measurement -> {
                    Button btn_Edit = new Button("Edit");
                    Button btn_Delete = new Button("Delete");

                    btn_Edit.addClickListener(event -> {
                        // Placeholder action: We will implement functionality later
                        System.out.println("Edit button clicked for Measurement ID: " + measurement.getID());
                    });

                    btn_Delete.addClickListener(event -> {
                        System.out.println("Delete button clicked for Measurement ID: " + measurement.getID());
                    });
                       return new HorizontalLayout(btn_Edit, btn_Delete);
                });
        List<Measurement> measurements = measurementService.getMeasurementsByUsername(username.get().getUsername());
        grid.setItems(measurements);
        add(grid);

    }
}
