package com.bankonline.Final_Project.controllerstest;

import com.bankonline.Final_Project.DTOs.AccHolderTransferDTO;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import com.bankonline.Final_Project.repositories.users.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ThirdPartyControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Third Party chargeMoney works ok")
    void chargeMoney_works_Ok() throws Exception {
        AccHolderTransferDTO accHolderTransferDTO = new AccHolderTransferDTO(4L,6L, BigDecimal.valueOf(10L));
        String body = objectMapper.writeValueAsString(accHolderTransferDTO);
        MvcResult mvcResult = mockMvc.perform(put("/third-party/charge").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals("{\"currency\":\"EUR\",\"amount\":79990.00}",mvcResult.getResponse().getContentAsString());
    }

}
