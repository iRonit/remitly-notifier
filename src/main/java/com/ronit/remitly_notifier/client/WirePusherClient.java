package com.ronit.remitly_notifier.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class WirePusherClient {

    private static final String WIREPUSHER_URL = "https://wirepusher.com/send?";
    private static final String TITLE = "Remitly Alert!";
    private static final String MESSAGE = "Target remittance rate achieved: %s = %s";

    private static final String TYPE = "threshold-alert";
    private static final String ACTION = "https://www.remitly.com";

    private final RestTemplate restTemplate;

    public void pushNotification(final String deviceId, final String sourceRate, final String targetRate) {
        String urlTemplate = UriComponentsBuilder.fromUriString(WIREPUSHER_URL)
                .queryParam("id", "{id}")
                .queryParam("title", "{title}")
                .queryParam("message", "{message}")
                .queryParam("type", "{type}")
                .queryParam("action", "{action}")
                .encode().toUriString();
        ResponseEntity<Object> response = this.restTemplate
                .getForEntity(urlTemplate, Object.class, deviceId, TITLE, format(MESSAGE, sourceRate, targetRate), TYPE, ACTION);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("WirePush-ed to deviceID={}, sourceRate={}, targetRate={}", deviceId, sourceRate, targetRate);
        } else {
            log.error("Received a non-200 response from WirePush");
        }
    }
}
