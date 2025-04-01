package com.ronit.remitly_notifier.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserData {

    private String deviceId;
    private String conduit;
    private float target;
}
