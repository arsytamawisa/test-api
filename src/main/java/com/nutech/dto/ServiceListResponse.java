package com.nutech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceListResponse {
    private int status;
    private String message;

    @JsonProperty("data")
    private List<ServiceItem> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceItem {
        @JsonProperty("service_code")
        private String serviceCode;

        @JsonProperty("service_name")
        private String serviceName;

        @JsonProperty("service_icon")
        private String serviceIcon;

        @JsonProperty("service_tariff")
        private Long serviceTariff;
    }
}