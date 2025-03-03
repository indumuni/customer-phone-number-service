package com.belong.customer.phoneservice.controller;


import com.belong.customer.phoneservice.model.PhoneModel;
import com.belong.customer.phoneservice.model.PhoneResultsModel;
import com.belong.customer.phoneservice.model.PhonePatchModel;
import com.belong.customer.phoneservice.service.PhoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneController.class)
public class PhoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PhoneService phoneService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void findPhones_shouldContainPhoneDetailAndPageDetails_giveValidRequest() throws Exception {

        ArrayList<PhoneModel> content = new ArrayList<>();
        content.add(new PhoneModel(1L, 8448L, "+1234567890", "active"));

        when(phoneService.findPhonesBy(any(), anyInt(), anyInt()))
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

    @Test
    public void patchPhones_shouldReturnPhoneDetails_giveValidRequest() throws Exception {

        when(phoneService.updatePhoneStatus(anyLong(), anyString()))
                .thenReturn(new PhoneModel(1L, 8448L, "+1234567890", "active"));

        PhonePatchModel patchModel = new PhonePatchModel();
        patchModel.setStatus("suspended");

        mockMvc.perform(
                patch("/phones/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchModel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(8448L))
                .andExpect(jsonPath("$.number").value("+1234567890"))
                .andExpect(jsonPath("$.status").value("active"));

    }
}
