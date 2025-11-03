package com.nutech.controller;

import com.nutech.dto.ProfileRequest;
import com.nutech.dto.ProfileResponse;
import com.nutech.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {
        ProfileResponse response = profileService.getProfile();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(@RequestBody ProfileRequest request) {
        ProfileResponse response = profileService.updateProfile(request);
        return ResponseEntity.ok(response);
    }
}