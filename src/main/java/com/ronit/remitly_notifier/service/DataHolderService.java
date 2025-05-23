package com.ronit.remitly_notifier.service;

import com.ronit.remitly_notifier.dto.UserDataDTO;
import com.ronit.remitly_notifier.repository.model.UserConduitTarget;
import com.ronit.remitly_notifier.repository.model.UserData;
import com.ronit.remitly_notifier.repository.UserDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class DataHolderService {

    private final Set<UserDataDTO> cachedData = new HashSet<>(10);

    private final UserDataRepository userDataRepository;

    @Autowired
    public DataHolderService(final UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
        this.cachedData.addAll(getAllData());
    }

    public Set<UserDataDTO> getData() {
        return Collections.unmodifiableSet(this.cachedData);
    }

    public void register(final UserDataDTO userData) {
        saveData(userData);
        updateSet(this.cachedData, userData);
    }

    public void delete(final UserDataDTO userData) {
        this.userDataRepository.findById(userData.getDeviceId()).ifPresent(data -> {
            Optional<UserConduitTarget> conduit = data.getConduitTargets().stream()
                    .filter(c -> c.getConduit().equals(userData.getConduit()))
                    .findAny();
            conduit.ifPresent( conduitFound -> {
                data.getConduitTargets().remove(conduitFound);
                this.userDataRepository.save(data);
                this.cachedData.remove(userData);
                log.info("Data deleted: {}", userData);
            });
        });
    }

    public List<UserDataDTO> getUserDataForConduit(final String conduit) {
        return this.cachedData.stream()
                .filter(userData -> userData.getConduit().equals(conduit))
                .toList();
    }

    public Set<String> getAllConduits() {
        return this.cachedData.stream()
                .map(UserDataDTO::getConduit)
                .collect(Collectors.toSet());
    }

    private Set<UserDataDTO> getAllData() {
        List<UserData> all = this.userDataRepository.findAll();
        Set<UserDataDTO> result = new HashSet<>();
        all.forEach(userData -> result.addAll(mapUserConduitTargets(userData.getDeviceId(), userData.getConduitTargets())));
        return result;
    }

    private Set<UserDataDTO> mapUserConduitTargets(final String deviceId, final Set<UserConduitTarget> userConduitTargets) {
        return userConduitTargets.stream()
                .map(i -> UserDataDTO.mapUserConduitTarget(deviceId, i))
                .collect(Collectors.toSet());
    }

    private void saveData(final UserDataDTO userData) {
        UserData saved = this.userDataRepository.findById(userData.getDeviceId())
                .orElse(null);
        if(isNull(saved)) {
            saved = new UserData();
            saved.setDeviceId(userData.getDeviceId());
            saved.setConduitTargets(new HashSet<>());
            this.userDataRepository.saveAndFlush(saved);
        }
        UserConduitTarget newConduitTarget = new UserConduitTarget();
        newConduitTarget.setConduit(userData.getConduit());
        newConduitTarget.setTarget(userData.getTarget());
        updateSet(saved.getConduitTargets(), newConduitTarget);
        this.userDataRepository.save(saved);
    }

    private <T> void updateSet(final Set<T> set, final T item) {
        set.remove(item);
        set.add(item);
    }
}
