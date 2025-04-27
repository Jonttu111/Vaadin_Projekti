package com.example.application.views.Dialogs;


import com.example.application.entitys.Measurement;
import com.example.application.service.MeasurementService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;

public class MeasurementEditDialog extends Dialog {

    public MeasurementEditDialog(Measurement measurement, ListDataProvider<Measurement> dataProvider, MeasurementService measurementService) {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);

        Binder<Measurement> binder = new Binder<>(Measurement.class);

        IntegerField systolicField = new IntegerField("Systolic Pressure");
        IntegerField diastolicField = new IntegerField("Diastolic Pressure");
        IntegerField heartRateField = new IntegerField("Heart Rate");

        systolicField.setValue(measurement.getSystolicPressure());
        diastolicField.setValue(measurement.getDiastolicPressure());
        heartRateField.setValue(measurement.getHeartRate());

        systolicField.setMin(1);
        systolicField.setMax(300);
        systolicField.setHelperText("1-300");

        diastolicField.setMin(1);
        diastolicField.setMax(300);
        diastolicField.setHelperText("1-300");

        heartRateField.setMin(30);
        heartRateField.setMax(250);
        heartRateField.setHelperText("30-250");

        binder.forField(systolicField).asRequired("Systolic Pressure is required")
                .bind(
                        Measurement::getSystolicPressure,
                        Measurement::setSystolicPressure
                );
        binder.forField(diastolicField).asRequired("Diastolic Pressure is required")
                .bind(
                        Measurement::getDiastolicPressure,
                        Measurement::setDiastolicPressure
                );
        binder.forField(heartRateField).asRequired("Heart Rate is required")
                .bind(
                        Measurement::getHeartRate,
                        Measurement::setHeartRate
                );

        Button btn_Save = new Button("Save", event -> {
            if(binder.writeBeanIfValid(measurement)){
                measurementService.updateMeasurement(measurement);
                dataProvider.refreshAll();
                close();
            }
            else{
                Notification.show("Invalid input");
            }
        } );

        Button btn_Cancel = new Button("Cancel", event -> close());

        VerticalLayout layout = new VerticalLayout(systolicField, diastolicField, heartRateField, btn_Save);

        add(layout);

    }
}
