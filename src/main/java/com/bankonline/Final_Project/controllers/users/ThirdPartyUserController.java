package com.bankonline.Final_Project.controllers.users;

import com.bankonline.Final_Project.DTOs.AccHolderTransferDTO;
import com.bankonline.Final_Project.DTOs.ThirdPartyDTO;
import com.bankonline.Final_Project.Service.users.interfaces.ThirdPartyUserServiceInterface;
import com.bankonline.Final_Project.controllers.users.interfaces.ThirdPartyUserControllerInterface;
import com.bankonline.Final_Project.embedables.Money;
import io.swagger.v3.oas.annotations.headers.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ThirdPartyUserController implements ThirdPartyUserControllerInterface {
    @Autowired
    ThirdPartyUserServiceInterface thirdPartyUserServiceInterface;

    @PutMapping("/third-party/charge")
    @ResponseStatus(HttpStatus.OK)
    public Money chargeMoney(@RequestHeader String hashKey, @RequestBody ThirdPartyDTO thirdPartyDTO){
        return thirdPartyUserServiceInterface.transferMoneyByAccountType(hashKey, thirdPartyDTO.getOwnId(), thirdPartyDTO.getSecretKey(), thirdPartyDTO.getOtherId(), thirdPartyDTO.getAmount());
    }/*test done*/
}
