package com.example.application.views.MeasurementResults;

import com.example.application.entitys.Measurement;
import com.example.application.entitys.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.service.MeasurementService;
import com.example.application.views.Dialogs.MeasurementEditDialog;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@PageTitle("Results")
@Route(value = "results", layout = MainLayout.class)
@Menu(order = 2, icon = LineAwesomeIconUrl.DATABASE_SOLID)
@RolesAllowed( "USER")
public class MeasurementResults extends Div {
    private final MeasurementService measurementService;
    private final AuthenticatedUser authenticatedUser;

    public MeasurementResults(MeasurementService measurementService, AuthenticatedUser authenticatedUser) {
        this.measurementService = measurementService;
        this.authenticatedUser = authenticatedUser;

        Optional<User> username = authenticatedUser.get();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        List<Measurement> measurements = measurementService.getMeasurementsByUsername(username.get().getUsername());
        ListDataProvider<Measurement> dataProvider = new ListDataProvider<>(measurements);

        Grid<Measurement> grid = new Grid<>(Measurement.class, false);
        grid.setDataProvider(dataProvider);
        grid.addColumn(Measurement::getSystolicPressure).setHeader("Systolic Pressure");
        grid.addColumn(Measurement::getDiastolicPressure).setHeader("Diastolic Pressure");
        grid.addColumn(Measurement::getHeartRate).setHeader("Heart Rate");
        grid.addColumn(measurement -> measurement.getTimestamp().format(formatter))
                .setHeader("Timestamp").setAutoWidth(true).setComparator(Measurement::getTimestamp);


        grid.addComponentColumn(measurement -> {
                    Button btn_Edit = new Button("Edit");
                    Button btn_Delete = new Button("Delete");

                    btn_Edit.addClassNames(LumoUtility.Background.CONTRAST, LumoUtility.FontSize.LARGE);

                    btn_Delete.addClassNames(LumoUtility.Background.WARNING, LumoUtility.FontSize.LARGE);
                    btn_Edit.addClickListener(event -> {
                        // Placeholder action: We will implement functionality later
                        System.out.println("Edit button clicked for Measurement ID: " + measurement.getID());
                        MeasurementEditDialog editDialog = new MeasurementEditDialog(measurement, dataProvider, measurementService);
                        editDialog.open();
                    });

                    btn_Delete.addClickListener(event -> {
                        System.out.println("Delete button clicked for Measurement ID: " + measurement.getID());
                        boolean deleteTrue = measurementService.deleteMeasurement(measurement.getID());
                        if(deleteTrue){
                            dataProvider.getItems().remove(measurement);
                            dataProvider.refreshAll();
                        }

                    });
                       return new HorizontalLayout(btn_Edit, btn_Delete);
                });

        TextField filterField = new TextField("Search by date");
        filterField.setPlaceholder("dd.mm.yyyy hh:mm:ss");
        filterField.setClearButtonVisible(true);
        filterField.setValueChangeMode(ValueChangeMode.EAGER);
        filterField.addValueChangeListener(e -> {
            String filterText = e.getValue();
            if (filterText == null || filterText.isEmpty()) {
                dataProvider.clearFilters();
            } else {
                dataProvider.setFilter(measurement -> {
                    String formattedDate = measurement.getTimestamp().format(formatter);
                    return formattedDate.contains(filterText);
                });
            }
        });

        add(grid, filterField);
    }

}
