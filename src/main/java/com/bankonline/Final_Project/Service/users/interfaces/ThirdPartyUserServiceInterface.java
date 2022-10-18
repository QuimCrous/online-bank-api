package com.bankonline.Final_Project.Service.users.interfaces;

import com.bankonline.Final_Project.embedables.Money;

import java.math.BigDecimal;

public interface ThirdPartyUserServiceInterface {
    Money transferMoneyByAccountType(Long ownId, Long otherId, BigDecimal amount);
}
