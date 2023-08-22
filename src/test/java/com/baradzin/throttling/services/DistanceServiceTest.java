package com.baradzin.throttling.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.baradzin.throttling.exceptions.InvalidDataException;
import com.baradzin.throttling.models.responses.DistanceResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DistanceServiceTest {
    private DistanceService distanceService;

    @BeforeAll
    public void setUp() {
        distanceService = new DistanceService();
    }

    @Test
    public void successfulCalculationDistance() {
        double latitudeX = 10.00;
        double longitudeX = 11.00;
        double latitudeY = 15.00;
        double longitudeY = 34.00;
        assertEquals(
                new ResponseEntity<>(new DistanceResponse(2556.0), HttpStatus.OK),
                distanceService.calculateDistance(latitudeX, longitudeX, latitudeY, longitudeY));
    }

    @Test
    public void testWithNullValue() {
        double latitudeX = 10.00;
        double longitudeX = 11.00;
        double latitudeY = 15.00;
        assertThrows(
                InvalidDataException.class,
                () -> distanceService.calculateDistance(latitudeX, longitudeX, latitudeY, null));
    }

}