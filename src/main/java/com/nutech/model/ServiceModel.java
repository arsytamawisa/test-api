package com.nutech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "services")
@Data
public class ServiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "service_code", unique = true)
    private String serviceCode;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_icon")
    private String serviceIcon;

    @Column(name = "service_tariff")
    private Long serviceTariff;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}