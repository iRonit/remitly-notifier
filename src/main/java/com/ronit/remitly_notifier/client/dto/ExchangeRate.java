package com.ronit.remitly_notifier.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ExchangeRate {

    @JsonAlias("base_rate")
    private String baseRate;
}
