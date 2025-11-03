package com.nutech.service;

import com.nutech.dto.ProfileRequest;
import com.nutech.dto.ProfileResponse;
import com.nutech.model.User;
import com.nutech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ProfileResponse getProfile() {
        try {
            String email = getCurrentUserEmail();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return buildProfileResponse(user);

        } catch (Exception e) {
            return buildErrorResponse("Failed to get profile");
        }
    }

    @Transactional
    public ProfileResponse updateProfile(ProfileRequest request) {
        try {
            String email = getCurrentUserEmail();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Update menggunakan repository method
            userRepository.updateProfile(email, request.getFirstName(), request.getLastName());

            // Refresh user data
            user = userRepository.findByEmail(email).orElse(user);

            return buildProfileResponse(user);

        } catch (Exception e) {
            return buildErrorResponse("Failed to update profile");
        }
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        throw new RuntimeException("User not authenticated");
    }

    private ProfileResponse buildProfileResponse(User user) {
        ProfileResponse response = new ProfileResponse();
        response.setStatus(0);
        response.setMessage("Sukses");

        ProfileResponse.ProfileData data = new ProfileResponse.ProfileData();
        data.setEmail(user.getEmail());
        data.setFirstName(user.getFirstName());
        data.setLastName(user.getLastName());
        data.setProfileImage(user.getProfileImage());

        response.setData(data);
        return response;
    }

    private ProfileResponse buildErrorResponse(String message) {
        ProfileResponse response = new ProfileResponse();
        response.setStatus(108);
        response.setMessage(message);
        return response;
    }
}