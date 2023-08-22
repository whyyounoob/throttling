package com.baradzin.throttling.controllers;

import com.baradzin.throttling.models.responses.DistanceResponse;
import com.baradzin.throttling.services.DistanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/distance")
public class DistanceController {

    private final DistanceService distanceService;

    public DistanceController(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @Operation(summary = "Calculate distance between two coordinates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Distance value (kilometers)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DistanceResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Object> getDistance(
            @RequestParam Double latitudeX,
            @RequestParam Double longitudeX,
            @RequestParam Double latitudeY,
            @RequestParam Double longitudeY) {
        return distanceService.calculateDistance(latitudeX, longitudeX, latitudeY, longitudeY);
    }
}
