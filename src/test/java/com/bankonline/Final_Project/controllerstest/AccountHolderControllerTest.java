package com.bankonline.Final_Project.controllerstest;

import com.bankonline.Final_Project.DTOs.AccHolderTransferDTO;
import com.bankonline.Final_Project.DTOs.AddressDTO;
import com.bankonline.Final_Project.Service.users.AccountHolderService;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import com.bankonline.Final_Project.repositories.transactions.TransactionRepository;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountHolderControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderService accountHolderService;
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    @DisplayName("Account Holder getBalance works ok")
    void getBalance_works_ok() throws Exception {
        String body = objectMapper.writeValueAsString(2L);
        MvcResult mvcResult = mockMvc.perform(get("/account-holder/get-balance").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals(BigDecimal.valueOf(59200,2),accountRepository.findById(2L).get().getBalance().getAmount());
    }

    @Test
    @DisplayName("Account Holder getBalance works ok")
    void getBalance_throws_Not_Found() throws Exception {
        String body = objectMapper.writeValueAsString(90L);
        MvcResult mvcResult = mockMvc.perform(get("/account-holder/get-balance").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
        Assertions.assertTrue(mvcResult.getResolvedException().toString().contains("account"));
    }

    @Test
    @DisplayName("Account Holder transferMoney works ok")
    void transferMoney_works_Ok() throws Exception {
        AccHolderTransferDTO accHolderTransferDTO = new AccHolderTransferDTO(3L,4L,BigDecimal.valueOf(10L));
        String body = objectMapper.writeValueAsString(accHolderTransferDTO);
        MvcResult mvcResult = mockMvc.perform(put("/account-holder/transfer").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals("{\"currency\":\"EUR\",\"amount\":950.00}",mvcResult.getResponse().getContentAsString());
    }
    @Test
    @DisplayName("Account Holder transferMoney throws exception")
    void transferMoney_throws_Not_Acceptable() throws Exception {
        AccHolderTransferDTO accHolderTransferDTO = new AccHolderTransferDTO(1L,2L,BigDecimal.valueOf(2000L));
        String body = objectMapper.writeValueAsString(accHolderTransferDTO);
        MvcResult mvcResult = mockMvc.perform(put("/account-holder/transfer").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotAcceptable()).andReturn();
        Assertions.assertTrue(mvcResult.getResolvedException().toString().contains("Not enough money"));
    }

    @Test
    @DisplayName("Account Holder transferMoney throws exception")
    void transferMoney_throws_Not_Found() throws Exception {
        AccHolderTransferDTO accHolderTransferDTO = new AccHolderTransferDTO(50L,2L,BigDecimal.valueOf(50L));
        String body = objectMapper.writeValueAsString(accHolderTransferDTO);
        MvcResult mvcResult = mockMvc.perform(put("/account-holder/transfer").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
        Assertions.assertTrue(mvcResult.getResolvedException().toString().contains("owner"));

        AccHolderTransferDTO accHolderTransferDTO2 = new AccHolderTransferDTO(3L,50L,BigDecimal.valueOf(50L));
        String body2 = objectMapper.writeValueAsString(accHolderTransferDTO2);
        MvcResult mvcResult2 = mockMvc.perform(put("/account-holder/transfer").content(body2).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
        Assertions.assertTrue(mvcResult2.getResolvedException().toString().contains("receptor"));
    }
    @Test
    @DisplayName("Account Holder getAccounts works ok")
    void getAccounts_works_ok() throws Exception {
        String body = objectMapper.writeValueAsString(2L);
        MvcResult mvcResult = mockMvc.perform(get("/account-holder").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("Quim"));
    }

    @Test
    @DisplayName("Account Holder addMailingAddress works ok")
    void addMailingAddress_works_ok() throws Exception {
        AddressDTO addressDTO = new AddressDTO(2L,"calle patata 5","Piruletalandia","09990","IBIZAAAAA","PartyLand");
        String body = objectMapper.writeValueAsString(addressDTO);
        MvcResult mvcResult = mockMvc.perform(put("/account-holder/add-mailing-address").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("IBIZAAAAA"));
    }

    @Test
    @DisplayName("Account Holder transferMoney throws exception and Frozen account block due to fraud")
    void transferMoney_froze_account_works() throws Exception {
        AccHolderTransferDTO accHolderTransferDTO = new AccHolderTransferDTO(6L,4L,BigDecimal.valueOf(10L));
        String body = objectMapper.writeValueAsString(accHolderTransferDTO);
        MvcResult mvcResult = mockMvc.perform(put("/account-holder/transfer").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        AccHolderTransferDTO accHolderTransferDTO2 = new AccHolderTransferDTO(6L,4L,BigDecimal.valueOf(10L));
        String body2 = objectMapper.writeValueAsString(accHolderTransferDTO);
        MvcResult mvcResult2 = mockMvc.perform(put("/account-holder/transfer").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isIAmATeapot()).andReturn();
        Assertions.assertTrue(mvcResult2.getResolvedException().toString().contains("Your account has been FROZEN due to fraud actions"));

    }

    @Test
    @DisplayName("transferMoney throws exception when detects a frozen account")
    void transferMoney_frozen_throws_exception() throws Exception {
        AccHolderTransferDTO accHolderTransferDTO = new AccHolderTransferDTO(7L,7L,BigDecimal.valueOf(10L));
        String body = objectMapper.writeValueAsString(accHolderTransferDTO);
        MvcResult mvcResult = mockMvc.perform(put("/account-holder/transfer").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotAcceptable()).andReturn();
    }

    @Test
    @DisplayName("transferMoney throws exception strange amount comparison")
    void transferMoney_throws_exception_CheckStrangeAmount() throws Exception {
        AccHolderTransferDTO accHolderTransferDTO = new AccHolderTransferDTO(8L,7L,BigDecimal.valueOf(1000L));
        String body = objectMapper.writeValueAsString(accHolderTransferDTO);
        MvcResult mvcResult = mockMvc.perform(put("/account-holder/transfer").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
    }
}
