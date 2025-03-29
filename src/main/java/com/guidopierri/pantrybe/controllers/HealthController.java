package com.guidopierri.pantrybe.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(path = "health")
@Hidden
@Tag(name="Health")
@RestController
public class HealthController {


    @GetMapping("/live")
    public ResponseEntity<String> live() {
        log.debug("livenessProbe request received");

        return ResponseEntity.ok("UP");
}}
