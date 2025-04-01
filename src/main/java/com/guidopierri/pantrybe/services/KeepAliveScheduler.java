package com.guidopierri.pantrybe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class KeepAliveScheduler {

    private final RestTemplate restTemplate;
    private final String URL = "https://pantrybe.azurewebsites.net/api/v1/health/live";

    public KeepAliveScheduler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 30000) // 5 minutes
    public void keepAlive() {
        try {
            restTemplate.getForObject(URL, String.class);
            log.info("KeepAlive request sent successfully");
        } catch (Exception e) {
            log.warn("KeepAlive request failed", e);
        }
    }
}
