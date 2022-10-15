package com.bankonline.Final_Project.Service.users.interfaces;

import java.math.BigDecimal;

public interface AccountHolderServiceInterface {
    void transferMoney(Long ownId, Long otherId, BigDecimal amount);
}
