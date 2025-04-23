package com.example.application.entitys;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "measurements")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int ID;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    public LocalDateTime Timestamp;

    public int SystolicPressure;
    public int DiastolicPressure;
    public int HeartRate;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDateTime getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        Timestamp = timestamp;
    }

    public int getSystolicPressure() {
        return SystolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        SystolicPressure = systolicPressure;
    }

    public int getDiastolicPressure() {
        return DiastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        DiastolicPressure = diastolicPressure;
    }

    public int getHeartRate() {
        return HeartRate;
    }

    public void setHeartRate(int heartRate) {
        HeartRate = heartRate;
    }

}
