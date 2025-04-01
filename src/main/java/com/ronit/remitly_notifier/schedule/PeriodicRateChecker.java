package com.ronit.remitly_notifier.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class PeriodicRateChecker {

    @Scheduled(cron = "*/30 * * * *")
    public void checkPeriodically() {

    }
}
