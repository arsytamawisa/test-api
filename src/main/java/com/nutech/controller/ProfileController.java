package com.nutech.controller;

import com.nutech.dto.GenericResponse;
import com.nutech.dto.ProfileUpdateRequest;
import com.nutech.dto.ProfileResponse;
import com.nutech.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<GenericResponse<ProfileResponse>> getProfile(Authentication authentication) {
        String email = authentication.getName();

        ProfileResponse profile = profileService.getProfile(email);

        GenericResponse<ProfileResponse> response = GenericResponse.<ProfileResponse>builder()
                .status(0)
                .message("Sukses")
                .data(profile)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponse<ProfileResponse>> updateProfile(
            Authentication authentication,
            @RequestBody ProfileUpdateRequest request) {

        String email = authentication.getName();

        ProfileResponse updatedProfile = profileService.updateProfile(email, request);

        GenericResponse<ProfileResponse> response = GenericResponse.<ProfileResponse>builder()
                .status(0)
                .message("Sukses")
                .data(updatedProfile)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<ProfileResponse>> updateProfileImage(
            Authentication authentication,
            @RequestParam("file") MultipartFile file) {

        String email = authentication.getName();

        ProfileResponse updatedProfile = profileService.updateProfileImage(email, file);

        GenericResponse<ProfileResponse> response = GenericResponse.<ProfileResponse>builder()
                .status(0)
                .message("Sukses")
                .data(updatedProfile)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/image/{filename}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable String filename) {
        byte[] imageBytes = profileService.getProfileImage(filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // atau MediaType.IMAGE_PNG

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}