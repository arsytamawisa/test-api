package com.nutech.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class BannerResponse {
    private int status;
    private String message;
    private List<BannerData> data;

    @Data
    @Builder
    public static class BannerData {
        private String bannerName;
        private String bannerImage;
        private String description;
    }
}