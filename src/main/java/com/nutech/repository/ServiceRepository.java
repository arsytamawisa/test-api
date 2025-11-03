package com.nutech.repository;

import com.nutech.model.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, String> {
    List<ServiceModel> findAllByOrderByCreatedAtDesc();
    Optional<ServiceModel> findByServiceCode(String serviceCode);
    boolean existsByServiceCode(String serviceCode);
}