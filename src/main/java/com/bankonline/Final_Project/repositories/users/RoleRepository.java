package com.bankonline.Final_Project.repositories.users;

import com.bankonline.Final_Project.models.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
