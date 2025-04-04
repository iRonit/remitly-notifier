package com.ronit.remitly_notifier.service;

import com.ronit.remitly_notifier.dto.UserData;
import com.ronit.remitly_notifier.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class DataHolderService {

    private final Map<String, UserData> data = new ConcurrentHashMap<>(5);

    private final UserDataRepository userDataRepository;

    @Autowired
    public DataHolderService(final UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
        this.data.putAll(getAllData());
    }

    public Map<String, UserData> getData() {
        return Collections.unmodifiableMap(this.data);
    }

    public void registerData(final UserData userData) {
        saveData(userData);
        this.data.put(userData.getDeviceId(), userData);
    }

    public List<UserData> getUserDataForConduit(final String conduit) {
        return this.data.values().stream().filter(userData -> userData.getConduit().equals(conduit)).toList();
    }

    public Set<String> getAllConduits() {
        return this.data.values().stream().map(UserData::getConduit).collect(Collectors.toSet());
    }

    private Map<String, UserData> getAllData() {
        List<UserData> all = this.userDataRepository.findAll();
        return all.stream().collect(Collectors.toMap(UserData::getDeviceId, item -> item));
    }

    private void saveData(final UserData userData) {
        this.userDataRepository.save(userData);
    }
}
