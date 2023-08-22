package com.baradzin.throttling.models.responses;

import lombok.Data;

@Data
public class DistanceResponse {
    double distance;

    public DistanceResponse(double distance) {
        this.distance = distance;
    }

}
