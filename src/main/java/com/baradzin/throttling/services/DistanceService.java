package com.baradzin.throttling.services;

import com.baradzin.throttling.exceptions.InvalidDataException;
import com.baradzin.throttling.models.responses.DistanceResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * The DistanceService class provides methods for calculating the distance between two points on Earth
 * using their latitude and longitude coordinates.
 */
@Service
public class DistanceService {

    /**
     * Calculates the distance between two points on Earth using their latitude and longitude coordinates.
     *
     * @param latitudeX The latitude of the first point in degrees.
     * @param longitudeX The longitude of the first point in degrees.
     * @param latitudeY The latitude of the second point in degrees.
     * @param longitudeY The longitude of the second point in degrees.
     * @return A ResponseEntity containing a DistanceResponse object with the calculated distance
     *         between the two points in kilometers, rounded to the nearest whole number.
     * @throws InvalidDataException if any of the input coordinates are null.
     */
    public ResponseEntity<Object> calculateDistance(
            Double latitudeX,
            Double longitudeX,
            Double latitudeY,
            Double longitudeY) {
        if (latitudeX == null || longitudeX == null || latitudeY == null || longitudeY == null) {
            throw new InvalidDataException();
        }
        double theta = longitudeX - longitudeY;
        double distance = 60 * 1.1515 * (180 / Math.PI) * Math.acos(
                Math.sin(latitudeX * (Math.PI / 180)) * Math.sin(latitudeY * (Math.PI / 180)) +
                        Math.cos(latitudeX * (Math.PI / 180)) * Math.cos(latitudeY * (Math.PI
                                / 180)) * Math.cos(theta * (Math.PI / 180))
        );
        return new ResponseEntity<>(
                new DistanceResponse(Math.round(distance * 1.609344)),
                HttpStatus.OK);
    }

}
