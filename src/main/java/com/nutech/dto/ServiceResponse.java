package com.nutech.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ServiceResponse {
    private int status;
    private String message;
    private List<ServiceData> data;

    @Data
    @Builder
    public static class ServiceData {
        private String serviceCode;
        private String serviceName;
        private String serviceIcon;
        private int serviceTariff;
    }
}