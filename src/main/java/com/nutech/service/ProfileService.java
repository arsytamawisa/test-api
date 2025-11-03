package com.nutech.service;

import com.nutech.dto.ProfileUpdateRequest;
import com.nutech.dto.ProfileResponse;
import com.nutech.exception.BusinessException;
import com.nutech.model.User;
import com.nutech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final String UPLOAD_DIR = "uploads/profile-images/";

    @Transactional
    public ProfileResponse updateProfile(String email, ProfileUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User tidak ditemukan", 108));

        // Update profile data
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User updatedUser = userRepository.save(user);

        return mapToProfileResponse(updatedUser);
    }

    @Transactional
    public ProfileResponse updateProfileImage(String email, MultipartFile file) {

        // Validate file empty
        if (file.isEmpty()) {
            throw new BusinessException("File tidak boleh kosong", 102);
        }

        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new BusinessException("Format Image tidak sesuai", 102);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User tidak ditemukan", 108));

        try {
            // Create upload directory if not exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null ?
                    originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpeg";
            String filename = UUID.randomUUID() + fileExtension;

            // Save file
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            // Delete old profile image if exists
            if (user.getProfileImage() != null) {
                Path oldFilePath = uploadPath.resolve(user.getProfileImage());
                Files.deleteIfExists(oldFilePath);
            }

            // Update user profile image
            user.setProfileImage(filename);
            User updatedUser = userRepository.save(user);

            return mapToProfileResponse(updatedUser);

        } catch (IOException e) {
            throw new BusinessException("Gagal mengupload gambar: " + e.getMessage(), 102);
        }
    }

    public ProfileResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User tidak ditemukan", 108));

        return mapToProfileResponse(user);
    }

    private ProfileResponse mapToProfileResponse(User user) {
        String profileImageUrl = null;
        if (user.getProfileImage() != null) {
            profileImageUrl = "/profile/image/" + user.getProfileImage();
        }

        return ProfileResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImage(profileImageUrl)
                .build();
    }

    public byte[] getProfileImage(String filename) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(filename);
            if (!Files.exists(filePath)) {
                throw new BusinessException("Gambar tidak ditemukan", 108);
            }
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new BusinessException("Gagal membaca gambar: " + e.getMessage(), 108);
        }
    }
}