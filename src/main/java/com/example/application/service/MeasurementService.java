package com.example.application.service;

import com.example.application.entitys.Measurement;
import com.example.application.entitys.User;
import com.example.application.repository.MeasurementRepository;
import com.example.application.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.Thread.sleep;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final UserService userService;

    public MeasurementService(MeasurementRepository measurementRepository, UserService userService) {
        this.measurementRepository = measurementRepository;
        this.userService = userService;

        Measurement measurement = new Measurement();
        measurement.setDiastolicPressure(33);
        measurement.setSystolicPressure(123);
        measurement.setHeartRate(60);

        Measurement measurement2 = new Measurement();
        measurement2.setDiastolicPressure(53);
        measurement2.setSystolicPressure(100);
        measurement2.setHeartRate(70);

        saveMeasurement(measurement, "1");
        saveMeasurement(measurement2, "2");

    }


    public Measurement saveMeasurement(Measurement measurement, String username){
        User user = userService.findByUsername(username).orElseThrow(() -> new IllegalStateException("User not found"));
        measurement.setTimestamp(LocalDateTime.now());
        measurement.setUser(user);
        user.getMeasurements().add(measurement);
        measurementRepository.save(measurement);
        return measurement;
    }

    public List<Measurement> getAllMeasurements(){
        return measurementRepository.findAll();
    }

    public List<Measurement> getMeasurementsByUsername(String username){
        User user = userService.findByUsername(username).orElseThrow(() -> new IllegalStateException("User not found"));

        return user.getMeasurements();
    }

}
