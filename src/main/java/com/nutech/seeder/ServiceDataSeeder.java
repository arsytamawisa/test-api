package com.nutech.seeder;

import com.nutech.model.ServiceModel;
import com.nutech.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceDataSeeder implements CommandLineRunner {

    private final ServiceRepository serviceRepository;

    @Override
    public void run(String... args) throws Exception {
        if (serviceRepository.count() == 0) {
            List<ServiceModel> services = Arrays.asList(
                    ServiceModel.builder()
                            .serviceCode("PAJAK")
                            .serviceName("Pajak PBB")
                            .serviceIcon("https://nutech-integrasi.com/icon/pajak.png")
                            .serviceTariff(40000L)
                            .build(),
                    ServiceModel.builder()
                            .serviceCode("PLN")
                            .serviceName("Listrik")
                            .serviceIcon("https://nutech-integrasi.com/icon/pln.png")
                            .serviceTariff(10000L)
                            .build(),
                    // ... tambahkan services lainnya
                    ServiceModel.builder()
                            .serviceCode("PDAM")
                            .serviceName("PDAM Berlangganan")
                            .serviceIcon("https://nutech-integrasi.com/icon/pdam.png")
                            .serviceTariff(40000L)
                            .build()
            );

            serviceRepository.saveAll(services);
            System.out.println("Sample services created!");
        }
    }
}