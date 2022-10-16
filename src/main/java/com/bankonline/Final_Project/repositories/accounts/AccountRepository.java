package com.bankonline.Final_Project.repositories.accounts;

import com.bankonline.Final_Project.models.accounts.Account;
import com.bankonline.Final_Project.models.users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByPrimaryOwner(AccountHolder primaryOwner);
}
