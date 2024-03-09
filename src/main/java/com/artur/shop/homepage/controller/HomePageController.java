package com.artur.shop.homepage.controller;

import com.artur.shop.homepage.controller.dto.HomePageDto;
import com.artur.shop.homepage.service.HomePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomePageController {

    private final HomePageService homePageService;
    @GetMapping("/homePage")
    @Cacheable("homePage")
    public HomePageDto getMainPage() {
        return new HomePageDto(homePageService.getDiscountedProducts());
    }
}
