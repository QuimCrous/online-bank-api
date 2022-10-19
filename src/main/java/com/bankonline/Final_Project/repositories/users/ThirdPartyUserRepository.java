package com.bankonline.Final_Project.repositories.users;

import com.bankonline.Final_Project.models.users.ThirdPartyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdPartyUserRepository extends JpaRepository<ThirdPartyUser, Long> {
    Optional<String> findByHashedKey(String hashKey);
}
