package com.example.application.views.Measurement;
import com.example.application.entitys.Measurement;
import com.example.application.entitys.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.service.MeasurementService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.themes.LumoDarkTheme;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.LocalDateTime;
import java.util.Optional;


@PageTitle("Measurement")
@Route(value = "measurement", layout = MainLayout.class)
@Menu(order = 1, icon = LineAwesomeIconUrl.WPFORMS)
@RolesAllowed("USER")
public class MeasurementView extends Div {
    private final MeasurementService measurementService;
    private final AuthenticatedUser authenticatedUser;
    private final Binder<Measurement> binder = new Binder<>(Measurement.class);

    public MeasurementView(MeasurementService measurementService, AuthenticatedUser authenticatedUser) {
        this.measurementService = measurementService;
        this.authenticatedUser = authenticatedUser;

        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        IntegerField ifSystolicPressure = new IntegerField("Systolic Pressure");
        IntegerField ifDiastolicPressure = new IntegerField("Diastolic Pressure");
        IntegerField ifHeartRate = new IntegerField("Heart Rate");
        Button btnSave = new Button("Save");
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        ifSystolicPressure.setMin(1);
        ifSystolicPressure.setMax(300);
        ifSystolicPressure.setHelperText("1-300");
        ifSystolicPressure.setErrorMessage("Systolic Pressure must be between 1 and 300");

        ifDiastolicPressure.setMin(1);
        ifDiastolicPressure.setMax(300);
        ifDiastolicPressure.setHelperText("1-300");
        ifDiastolicPressure.setErrorMessage("Diastolic Pressure must be between 1 and 300");

        ifHeartRate.setMin(30);
        ifHeartRate.setMax(250);
        ifHeartRate.setHelperText("30-250");
        ifHeartRate.setErrorMessage("Heart Rate must be between 30 and 250");

        binder.forField(ifSystolicPressure).asRequired("Systolic Pressure is required")
                .bind(
                        Measurement::getSystolicPressure,
                        Measurement::setSystolicPressure
                );
        binder.forField(ifDiastolicPressure).asRequired("Diastolic Pressure is required")
                .bind(
                        Measurement::getDiastolicPressure,
                        Measurement::setDiastolicPressure
                );
        binder.forField(ifHeartRate).asRequired("Heart Rate is required")
                .bind(
                        Measurement::getHeartRate,
                        Measurement::setHeartRate
                );

        btnSave.addClickListener(e -> measurementSave());

        //FormLayout measurementForm = new FormLayout();
        //measurementForm.add(ifSystolicPressure, ifDiastolicPressure, ifHeartRate, btnSave);
        layout.add(ifSystolicPressure, ifDiastolicPressure, ifHeartRate, btnSave);
        setSizeFull();
        add(layout);
    }

    private void measurementSave(){
        Measurement measurement = new Measurement();
        Optional<User> username = authenticatedUser.get();
        if(binder.writeBeanIfValid(measurement)){
            this.measurementService.saveMeasurement(measurement, username.get().getUsername());
            Notification.show("Measurement saved", 3000, Notification.Position.BOTTOM_CENTER);
            binder.readBean(null);
        }
        else{
            Notification.show("Invalid input");
        }
    }
}
