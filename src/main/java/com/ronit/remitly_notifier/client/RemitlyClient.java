package com.ronit.remitly_notifier.client;

import com.ronit.remitly_notifier.client.dto.RemitlyClientResponse;
import com.ronit.remitly_notifier.client.dto.RemitlyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.rmi.ServerException;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class RemitlyClient {

    private static final String REMITLY_URL = "https://api.remitly.io/v3/calculator/estimate";
    private final RestTemplate restTemplate;

    public RemitlyClientResponse fetchCurrentRate(final String conduit) throws ServerException {
        RemitlyResponse response = fireApiCallToExternal(conduit);
        try {
            float currentRate = Float.parseFloat(response.getEstimate().getExchangeRate().getBaseRate());
            return RemitlyClientResponse.builder()
                    .currentRate(currentRate)
                    .sourceCurrencySymbol(response.getEstimate().getConduit().getSourceCurrency().getSymbol())
                    .targetCurrencySymbol(response.getEstimate().getConduit().getTargetCurrency().getSymbol())
                    .build();
        } catch (Exception anyEx) {
            log.error("Couldn't parse the current rate", anyEx);
            throw new ServerException("Couldn't parse the current rate ", anyEx);
        }
    }

    private RemitlyResponse fireApiCallToExternal(final String conduit) throws ServerException {
        String urlTemplate = UriComponentsBuilder.fromUriString(REMITLY_URL)
                .queryParam("conduit", "{conduit}")
                .queryParam("amount", "1")
                .encode().toUriString();
        ResponseEntity<RemitlyResponse> response = this.restTemplate
                .getForEntity(urlTemplate, RemitlyResponse.class, conduit);
        if (isNull(response.getBody())) {
            log.error("Empty response body from : {}", REMITLY_URL);
            throw new ServerException("Empty Response received from Remitly");
        }
        return response.getBody();
    }
}
