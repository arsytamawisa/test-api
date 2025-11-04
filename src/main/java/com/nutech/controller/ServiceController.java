package com.nutech.controller;

import com.nutech.dto.ServiceItemResponse;
import com.nutech.service.LayananPPOBService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {

    private final LayananPPOBService layananPPOBService;

    @GetMapping
    public List<ServiceItemResponse> getAllServices() {
        return layananPPOBService.getServices();
    }
}