package com.bankonline.Final_Project.controllers.users.interfaces;

import com.bankonline.Final_Project.DTOs.ThirdPartyDTO;
import com.bankonline.Final_Project.embedables.Money;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface ThirdPartyUserControllerInterface {
    Money chargeMoney(@RequestHeader String hashKey, @RequestBody ThirdPartyDTO thirdPartyDTO);
}
