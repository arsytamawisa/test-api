package com.nutech.service;

import com.nutech.dto.ServiceResponse;
import com.nutech.model.ServiceModel;
import com.nutech.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LayananPPOBService {

    private final ServiceRepository serviceRepository;

    public ServiceResponse getServices() {
        try {
            List<ServiceModel> services = serviceRepository.findAllByOrderByCreatedAtDesc();

            List<ServiceResponse.ServiceData> serviceData = services.stream()
                    .map(this::convertToServiceData)
                    .collect(Collectors.toList());

            return ServiceResponse.builder()
                    .status(0)
                    .message("Sukses")
                    .data(serviceData)
                    .build();

        } catch (Exception e) {
            return ServiceResponse.builder()
                    .status(108)
                    .message("Terjadi kesalahan")
                    .data(null)
                    .build();
        }
    }

    private ServiceResponse.ServiceData convertToServiceData(ServiceModel serviceModel) {
        return ServiceResponse.ServiceData.builder()
                .serviceCode(serviceModel.getServiceCode())
                .serviceName(serviceModel.getServiceName())
                .serviceIcon(serviceModel.getServiceIcon())
                .serviceTariff(serviceModel.getServiceTariff().intValue())
                .build();
    }
}