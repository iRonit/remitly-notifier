package com.ronit.remitly_notifier.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class Conduit {

    @JsonAlias("source_currency")
    private Currency sourceCurrency;

    @JsonAlias("target_currency")
    private Currency targetCurrency;
}
