package com.belong.customer.phoneservice.controller;


import com.belong.customer.phoneservice.model.PhoneModel;
import com.belong.customer.phoneservice.model.PhoneResultsModel;
import com.belong.customer.phoneservice.service.PhoneService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneController.class)
public class PhoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PhoneService phoneService;


    @Test
    public void findPhones_ShouldContainPhoneDetailAndPageDetails() throws Exception {

        ArrayList<PhoneModel> content = new ArrayList<>();
        content.add(new PhoneModel(1L, 8448L, "+1234567890", "active"));
        Mockito.when(phoneService.findPhonesBy(any(), anyInt(), anyInt()))
                .thenReturn(new PhoneResultsModel(0, 1, content));

        mockMvc.perform(get("/phones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pageNo").value(0))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].customerId").value(8448))
                .andExpect(jsonPath("$.content[0].status").value("active"))
                .andExpect(jsonPath("$.content[0].number").value("+1234567890"));
    }
}
