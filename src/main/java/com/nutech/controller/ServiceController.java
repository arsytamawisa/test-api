package com.nutech.controller;

import com.nutech.dto.ServiceResponse;
import com.nutech.service.LayananPPOBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {

    private final LayananPPOBService servicePPOB;

    @GetMapping
    public ResponseEntity<ServiceResponse> getServices() {
        ServiceResponse response = servicePPOB.getServices();
        return ResponseEntity.ok(response);
    }
}