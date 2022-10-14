package com.bankonline.Final_Project.repositories.users;

import com.bankonline.Final_Project.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
