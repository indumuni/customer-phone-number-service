package com.belong.customer.phoneservice.controller;

import com.belong.customer.phoneservice.domain.Phone;
import com.belong.customer.phoneservice.repository.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PhoneControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PhoneRepository phoneRepository;

    @BeforeEach
    public void setUp() {

        phoneRepository.deleteAll();

        List<Phone> phoneResults = new ArrayList<>();

        phoneResults.add(new Phone(3217L, "+1234567890", "active"));
        phoneResults.add(new Phone(8448L, "+9876543210", "active"));
        phoneResults.add(new Phone(8448L, "+1122334455", "suspended"));
        phoneResults.add(new Phone(1925L, "+9988776655", "active"));
        phoneResults.add(new Phone(4032L, "+5544332211", "inactive"));

        phoneRepository.saveAll(phoneResults);
    }

    @Test
    public void getPhones_FilterPhoneByCustomer_GiveValidCustomer() throws Exception {
        mockMvc.perform(get("/phones?customerId=8448")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNo").value(0))
                .andExpect(jsonPath("$.total").value(2))
                .andExpect(jsonPath("$.content[0].number").value("+9876543210"))
                .andExpect(jsonPath("$.content[0].status").value("active"))
                .andExpect(jsonPath("$.content[1].number").value("+1122334455"))
                .andExpect(jsonPath("$.content[1].status").value("suspended"));
    }

    @Test
    public void getPhones_ReturnEveryThing_GiveNoCustomer() throws Exception {
        mockMvc.perform(get("/phones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNo").value(0))
                .andExpect(jsonPath("$.total").value(5))
                .andExpect(jsonPath("$.content[0].number").value("+1234567890"))
                .andExpect(jsonPath("$.content[1].number").value("+9876543210"))
                .andExpect(jsonPath("$.content[2].number").value("+1122334455"))
                .andExpect(jsonPath("$.content[3].number").value("+9988776655"))
                .andExpect(jsonPath("$.content[4].number").value("+5544332211"));

    }

    @Test
    public void getPhones_ShouldPaginate_GivePageDetails() throws Exception {
        mockMvc.perform(get("/phones?pageNo=1&pageSize=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNo").value(1))
                .andExpect(jsonPath("$.total").value(5))
                .andExpect(jsonPath("$.content[0].number").value("+9988776655"))
                .andExpect(jsonPath("$.content[1].number").value("+5544332211"));

    }
}
