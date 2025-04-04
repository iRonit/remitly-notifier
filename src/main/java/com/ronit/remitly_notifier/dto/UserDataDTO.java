package com.ronit.remitly_notifier.dto;

import com.ronit.remitly_notifier.repository.model.UserConduitTarget;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class UserDataDTO {

    private String deviceId;
    private String conduit;
    private float target;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataDTO that = (UserDataDTO) o;
        return Objects.equals(deviceId, that.deviceId) && Objects.equals(conduit, that.conduit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, conduit);
    }

    public static UserDataDTO mapUserConduitTarget(final String deviceId, final UserConduitTarget userConduitTarget) {
        return UserDataDTO.builder()
                .deviceId(deviceId)
                .conduit(userConduitTarget.getConduit())
                .target(userConduitTarget.getTarget())
                .build();
    }
}
