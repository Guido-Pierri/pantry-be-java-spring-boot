package com.guidopierri.pantrybe.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(path = "health")
@Hidden
@Tag(name="Health")
@RestController
public class HealthController {
    private final JdbcTemplate jdbcTemplate;
public HealthController(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}
    @GetMapping("/live")
    public ResponseEntity<String> live() {
        log.info("livenessProbe request received");

        return new ResponseEntity<>("ok", HttpStatusCode.valueOf(200));
}
    @GetMapping("/ready")
    public ResponseEntity<String> ready() {
        log.debug("readinessProbe request received");
        try {
            // Check database connectivity
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Readiness check failed", e);
            return new ResponseEntity<>("Database connection failed", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
