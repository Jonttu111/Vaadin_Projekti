package com.example.application.views.Measurement;
import com.example.application.entitys.Measurement;
import com.example.application.service.MeasurementService;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;




@PageTitle("Measurement")
@Route("measurement")
@Menu(order = 1, icon = LineAwesomeIconUrl.HOME_SOLID)
public class MeasurementView extends VerticalLayout {
    private final MeasurementService measurementService;

    private final IntegerField ifSystolicPressure = new IntegerField("Systolic Pressure");
    private final IntegerField ifDiastolicPressure = new IntegerField("Diastolic Pressure");
    private final IntegerField ifHeartRate = new IntegerField("Heart Rate");
    private final Button btnSave = new Button("Save");
    private final Button btnCancel = new Button("Cancel");

    private final Binder<Measurement> binder = new Binder<>(Measurement.class);
    public MeasurementView(MeasurementService measurementService) {
        this.measurementService = measurementService;
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        FormLayout measurementForm = new FormLayout();


        ifSystolicPressure.setMin(1);
        ifSystolicPressure.setMax(300);
        ifSystolicPressure.setErrorMessage("Systolic Pressure must be between 1 and 300");

        ifDiastolicPressure.setMin(1);
        ifDiastolicPressure.setMax(300);
        ifDiastolicPressure.setErrorMessage("Diastolic Pressure must be between 1 and 300");

        ifHeartRate.setMin(30);
        ifHeartRate.setMax(250);
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
        measurementForm.add(ifSystolicPressure, ifDiastolicPressure, ifHeartRate, btnSave, btnCancel);
        add(measurementForm);
    }

    private void measurementSave(){
        Measurement measurement = new Measurement();

        if(binder.writeBeanIfValid(measurement)){
            var x = this.measurementService.saveMeasurement(measurement);
            System.out.println(x.getID() + " " + x.getTimestamp() + x.getSystolicPressure() + x.getDiastolicPressure() + x.getHeartRate());
            Notification.show("Measurement saved");
        }
        else{
            Notification.show("Invalid input");
        }
    }
}
