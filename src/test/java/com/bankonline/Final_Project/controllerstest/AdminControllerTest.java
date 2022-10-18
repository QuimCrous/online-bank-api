package com.bankonline.Final_Project.controllerstest;

import com.bankonline.Final_Project.DTOs.*;
import com.bankonline.Final_Project.enums.Status;
import com.bankonline.Final_Project.repositories.accounts.AccountRepository;
import com.bankonline.Final_Project.repositories.users.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AdminControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());




    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


    }

    @Test
    @DisplayName("Admin modify balance works ok")
    void modifyBalance_works_ok() throws Exception {
        ModifyBalanceDTO modifyBalanceDTO = new ModifyBalanceDTO(1L,BigDecimal.valueOf(10L),"increase");
        String body = objectMapper.writeValueAsString(modifyBalanceDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/admin/modify-balance").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        Assertions.assertEquals(new BigDecimal("1010.00"),accountRepository.findById(1L).get().getBalance().getAmount());
    }

    @Test
    @DisplayName("Admin change status works ok")
    void changeStatusAccount_works_ok() throws Exception {
        AccountStatusDTO accountStatusDTO = new AccountStatusDTO(2L,"FROZEN");
        String body = objectMapper.writeValueAsString(accountStatusDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/admin/status").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        Assertions.assertEquals(Status.FROZEN,accountRepository.findById(2L).get().getStatus());
    }

    @Test
    @DisplayName("what")
    void createNewUserAccount_works_ok() throws Exception {
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("Pablo","mail@test.com","123456789", LocalDate.of(1987,04,05),"savingsaccount",BigDecimal.valueOf(1500L),BigDecimal.valueOf(1500L),BigDecimal.valueOf(0.15));
        String body = objectMapper.writeValueAsString(accountHolderDTO);
        MvcResult mvcResult = mockMvc.perform(post("/admin/create-new-user-account").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
    }

    @Test
    @DisplayName("Admin create new account by user works ok")
    void createNewAccountByUser_works_ok() throws Exception {
        CreateAccountDTO createAccountDTO = new CreateAccountDTO(2L,"creditcard",BigDecimal.valueOf(500L),BigDecimal.valueOf(500L),BigDecimal.valueOf(0.15));
        String body = objectMapper.writeValueAsString(createAccountDTO);
        MvcResult mvcResult = mockMvc.perform(post("/admin/create-new-account-by-user").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        Assertions.assertEquals(7L,accountRepository.findById(8L).get().getPrimaryOwner().getUserId());
    }

    @Test
    @DisplayName("Admin get all users works ok")
    void getAllUsers_works_ok() throws  Exception {
        MvcResult mvcResult = mockMvc.perform(get("/admin/all-users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Quim"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Oscar"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Danny"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Jaume"));
    }

    @Test
    @DisplayName("Admin delete account works ok")
    void deleteAccount_works_ok() throws Exception {
        String body = objectMapper.writeValueAsString(5);
        MvcResult mvcResult = mockMvc.perform(delete("/admin/delete-account").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        assertTrue(!accountRepository.existsById(5L));
    }

    @Test
    @DisplayName("Admin add secondary owner works ok")
    void addSecondaryOwner() throws  Exception {
        AddSecondOwnerDTO addSecondOwnerDTO = new AddSecondOwnerDTO(1L,3L);
        String body = objectMapper.writeValueAsString(addSecondOwnerDTO);
        MvcResult mvcResult = mockMvc.perform(put("/admin/add-second-owner").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals(3L,accountRepository.findById(1L).get().getSecondaryOwner().getUserId());
    }

    @Test
    @DisplayName("Admin get all accounts works ok")
    void getAllAccounts_works_ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/admin/get-all-accounts").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("{\"id\":1,\"balance\":{\"currency\":\"EUR\",\"amount\":1000.00}"));
    }

}
