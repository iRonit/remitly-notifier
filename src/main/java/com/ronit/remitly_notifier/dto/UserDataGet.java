package com.ronit.remitly_notifier.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDataGet {

    private List<UserData> availableData;
}
