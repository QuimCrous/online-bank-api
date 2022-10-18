package com.bankonline.Final_Project.controllers.users.interfaces;

import com.bankonline.Final_Project.DTOs.AccHolderTransferDTO;
import com.bankonline.Final_Project.embedables.Money;
import org.springframework.web.bind.annotation.RequestBody;

public interface ThirdPartyUserControllerInterface {
    Money chargeMoney(@RequestBody AccHolderTransferDTO accHolderTransferDTO);
}
