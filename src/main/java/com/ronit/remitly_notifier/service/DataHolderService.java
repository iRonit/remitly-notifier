package com.ronit.remitly_notifier.service;

import com.ronit.remitly_notifier.dto.UserData;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class DataHolderService {

    @Getter
    private final Map<String, UserData> data = new ConcurrentHashMap<>(5);

    public List<UserData> getUserDataForConduit(final String conduit) {
        return getData().values().stream().filter(userData -> userData.getConduit().equals(conduit)).toList();
    }

    public Set<String> getAllConduits() {
        return getData().values().stream().map(UserData::getConduit).collect(Collectors.toSet());
    }
}
