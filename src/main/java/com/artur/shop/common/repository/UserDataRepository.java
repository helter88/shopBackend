package com.artur.shop.common.repository;

import com.artur.shop.common.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    UserData findByUserId(Long userId);
}
