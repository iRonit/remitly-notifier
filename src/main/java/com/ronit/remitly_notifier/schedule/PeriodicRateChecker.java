package com.ronit.remitly_notifier.schedule;

import com.ronit.remitly_notifier.client.RemitlyClient;
import com.ronit.remitly_notifier.client.WirePusherClient;
import com.ronit.remitly_notifier.client.dto.RemitlyClientResponse;
import com.ronit.remitly_notifier.dto.UserData;
import com.ronit.remitly_notifier.service.DataHolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeriodicRateChecker {

    //    private static final long THIRTY_MINUTES_IN_MS = 30 * 60 * 1000L;
    private static final long THIRTY_MINUTES_IN_MS = 2 * 60 * 1000L;

    private final RemitlyClient remitlyClient;
    private final WirePusherClient wirePusherClient;
    private final DataHolderService dataHolderService;

    @Scheduled(fixedDelay = THIRTY_MINUTES_IN_MS)
    public void checkPeriodically() throws ServerException {
        log.info("Periodic Run has started");
        // TODO: parallelize below
        for (String conduit : this.dataHolderService.getAllConduits()) {
            checkPerConduit(conduit);
        }
    }

    private void checkPerConduit(final String conduit) throws ServerException {
        log.info("Checking for conduit: {}", conduit);
        RemitlyClientResponse currentRateDetails = this.remitlyClient.fetchCurrentRate(conduit);
        log.info("Current Rate Details = {}", currentRateDetails);
        List<UserData> filteredUsers = this.dataHolderService.getUserDataForConduit(conduit);
        // TODO: parallelize below
        for (UserData userData : filteredUsers) {
            checkIfTargetMetAndPush(userData, currentRateDetails);
        }
    }

    private void checkIfTargetMetAndPush(final UserData userData, final RemitlyClientResponse currentRateDetails) {
        if (currentRateDetails.getCurrentRate() >= userData.getTarget()) {
            log.info("Target has been achieved for deviceId= {}, Target= {}, CurrentRate = {}",
                    userData.getDeviceId(), userData.getTarget(), currentRateDetails.getCurrentRate());
            String sourceRate = currentRateDetails.getSourceCurrencySymbol() + "1";
            String targetRate = currentRateDetails.getTargetCurrencySymbol() + currentRateDetails.getCurrentRate();
            this.wirePusherClient.pushNotification(userData.getDeviceId(), sourceRate, targetRate);
        }
    }
}
