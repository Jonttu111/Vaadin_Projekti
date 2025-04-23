package com.example.application.Entitys;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Measurement {
    public int ID;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    public Date Timestamp;
    public int BloodPressure;
    public int HeartRate;
}
