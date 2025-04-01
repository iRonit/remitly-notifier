package com.ronit.remitly_notifier.client.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemitlyClientResponse {

    private float currentRate;
    private String sourceCurrencySymbol;
    private String targetCurrencySymbol;
}
