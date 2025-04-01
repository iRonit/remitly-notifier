package com.ronit.remitly_notifier.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class Estimate {

    @JsonAlias("exchange_rate")
    private ExchangeRate exchangeRate;

    private Conduit conduit;
}
