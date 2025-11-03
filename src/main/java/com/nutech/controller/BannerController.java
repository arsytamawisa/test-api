package com.nutech.controller;

import com.nutech.dto.BannerResponse;
import com.nutech.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banner")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public ResponseEntity<BannerResponse> getBanners() {
        BannerResponse response = bannerService.getBanners();
        return ResponseEntity.ok(response);
    }
}