package com.ronit.remitly_notifier.controller;

import com.ronit.remitly_notifier.dto.UserDataDTO;
import com.ronit.remitly_notifier.dto.UserDataGet;
import com.ronit.remitly_notifier.service.DataHolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/data")
@RequiredArgsConstructor
@Slf4j
public class DataHolderController {

    private final DataHolderService dataHolderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerData(@RequestBody final UserDataDTO data) {
        this.dataHolderService.registerData(data);
        log.info("Data registered: {}", data);
    }

    @GetMapping
    public UserDataGet getAllData() {
        return UserDataGet.builder()
                .availableData(this.dataHolderService.getData())
                .build();
    }
}
