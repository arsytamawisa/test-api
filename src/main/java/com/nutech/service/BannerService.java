package com.nutech.service;

import com.nutech.dto.BannerResponse;
import com.nutech.model.Banner;
import com.nutech.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public BannerResponse getBanners() {
        try {
            List<Banner> banners = bannerRepository.findAllByOrderByCreatedAtDesc();

            List<BannerResponse.BannerData> bannerData = banners.stream()
                    .map(this::convertToBannerData)
                    .collect(Collectors.toList());

            return BannerResponse.builder()
                    .status(0)
                    .message("Sukses")
                    .data(bannerData)
                    .build();

        } catch (Exception e) {
            return BannerResponse.builder()
                    .status(108)
                    .message("Terjadi kesalahan")
                    .data(null)
                    .build();
        }
    }

    private BannerResponse.BannerData convertToBannerData(Banner banner) {
        return BannerResponse.BannerData.builder()
                .bannerName(banner.getBannerName())
                .bannerImage(banner.getBannerImage())
                .description(banner.getDescription())
                .build();
    }
}