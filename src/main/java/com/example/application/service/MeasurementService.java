package com.example.application.service;

import com.example.application.entitys.Measurement;
import com.example.application.repository.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }
    public Measurement saveMeasurement(Measurement measurement){
        measurement.setTimestamp(LocalDateTime.now());

        return measurementRepository.save(measurement);
    }
}
