package com.nutech.dto;

import lombok.Data;

@Data
public class ProfileResponse {
    private int status;
    private String message;
    private ProfileData data;

    @Data
    public static class ProfileData {
        private String email;
        private String firstName;
        private String lastName;
        private String profileImage;
    }
}