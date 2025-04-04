package com.ronit.remitly_notifier.repository;

import com.ronit.remitly_notifier.repository.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, String> {
    UserData findByDeviceId(String deviceId);
}
