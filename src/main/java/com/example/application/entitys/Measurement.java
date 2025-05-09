package com.example.application.entitys;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
@Entity
@Table(name = "measurements")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private LocalDateTime Timestamp;

    private int SystolicPressure;
    private int DiastolicPressure;
    private int HeartRate;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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
        this.Timestamp = timestamp;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
