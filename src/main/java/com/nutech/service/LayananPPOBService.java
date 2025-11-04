package com.nutech.service;

import com.nutech.dto.ServiceItemResponse;
import com.nutech.model.ServiceModel;
import com.nutech.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LayananPPOBService {

    private final ServiceRepository serviceRepository;

    public List<ServiceItemResponse> getServices() {
        List<ServiceModel> services = serviceRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        return services.stream()
                .map(service -> ServiceItemResponse.builder()
                        .serviceCode(service.getServiceCode())
                        .serviceName(service.getServiceName())
                        .serviceIcon(service.getServiceIcon())
                        .serviceTariff(service.getServiceTariff())
                        .build())
                .collect(Collectors.toList());
    }
}