package com.belong.customer.phoneservice.controller;


import com.belong.customer.phoneservice.model.PhoneResultsModel;
import com.belong.customer.phoneservice.service.PhoneService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @MockBean
    private PhoneService phoneService;


    @Test
    public void findPhoneBy_ShouldUseDefaultPageNo_givePageMissingFromRequest() throws Exception {

        Mockito.when(phoneService.findPhonesBy(any(), anyInt(), anyInt()))
                .thenReturn(new PhoneResultsModel(1, 1, 1, null));

        mockMvc.perform(get("/phones"))  // Simulate GET request
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pageNo").value(1))
                .andExpect(jsonPath("$.pageSize").value(1));
    }
}
