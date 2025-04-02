package com.ronit.remitly_notifier.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/health")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<Void> ping() {
        return ResponseEntity.ok().build();
    }
}
