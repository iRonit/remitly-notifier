package com.ronit.remitly_notifier.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDataGet {

    private Set<UserDataDTO> availableData;
}
