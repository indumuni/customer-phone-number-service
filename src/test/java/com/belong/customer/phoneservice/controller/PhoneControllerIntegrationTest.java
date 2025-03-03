package com.belong.customer.phoneservice.controller;

import com.belong.customer.phoneservice.domain.Phone;
import com.belong.customer.phoneservice.model.PhonePatchModel;
import com.belong.customer.phoneservice.repository.PhoneRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.belong.customer.phoneservice.domain.Status.ACTIVE;
import static com.belong.customer.phoneservice.domain.Status.EXPIRED;
import static com.belong.customer.phoneservice.domain.Status.INACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PhoneControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {

        phoneRepository.deleteAll();

        List<Phone> phoneResults = new ArrayList<>();

        phoneResults.add(new Phone(3217L, "+1234567890", ACTIVE));
        phoneResults.add(new Phone(8448L, "+9876543210", ACTIVE));
        phoneResults.add(new Phone(8448L, "+1122334455", EXPIRED));
        phoneResults.add(new Phone(1925L, "+9988776655", ACTIVE));
        phoneResults.add(new Phone(4032L, "+5544332211", INACTIVE));

        phoneRepository.saveAll(phoneResults);
    }

    @Test
    public void getPhones_filterPhoneByCustomer_giveValidCustomer() throws Exception {
        mockMvc.perform(get("/phones?customerId=8448")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNo").value(0))
                .andExpect(jsonPath("$.total").value(2))
                .andExpect(jsonPath("$.content[0].number").value("+9876543210"))
                .andExpect(jsonPath("$.content[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$.content[1].number").value("+1122334455"))
                .andExpect(jsonPath("$.content[1].status").value("EXPIRED"));
    }

    @Test
    public void getPhones_filterPhoneByCustomer_giveInvalidCustomer() throws Exception {
        mockMvc.perform(get("/phones?customerId=84488448")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNo").value(0))
                .andExpect(jsonPath("$.total").value(0));
    }

    @Test
    public void getPhones_filterPhoneByCustomer_giveBadCustomerNumber() throws Exception {
        mockMvc.perform(get("/phones?customerId=ABC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getPhones_returnEveryThing_giveNoCustomer() throws Exception {
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
    public void getPhones_shouldPaginate_givePageDetails() throws Exception {
        mockMvc.perform(get("/phones?pageNo=1&pageSize=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNo").value(1))
                .andExpect(jsonPath("$.total").value(5))
                .andExpect(jsonPath("$.content[0].number").value("+9988776655"))
                .andExpect(jsonPath("$.content[1].number").value("+5544332211"));

    }

    @Test
    public void patchPhoneStatus_shouldUpdatePhoneStatus_givenValidPhoneNumber() throws Exception {

        PhonePatchModel patchModel = new PhonePatchModel();
        patchModel.setStatus(EXPIRED);

        Optional<Phone> validPhone = phoneRepository.findByCustomerId(
                3217L,
                of(0, 100)
        ).stream().findFirst();

        if (validPhone.isEmpty()) {
            throw new Exception("Cannot find valid phone for testing");
        }

        Phone original = validPhone.get();
        Long id = original.getId();

        assertEquals(ACTIVE, original.getStatus());

        mockMvc.perform(
                        patch("/phones/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(patchModel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.customerId").value(3217))
                .andExpect(jsonPath("$.number").value("+1234567890"))
                .andExpect(jsonPath("$.status").value("EXPIRED"));
    }

    @Test
    public void patchPhoneStatus_shouldNotUpdatePhoneStatus_givenInvalidPhoneId() throws Exception {

        PhonePatchModel patchModel = new PhonePatchModel();
        patchModel.setStatus(EXPIRED);

        mockMvc.perform(
                        patch("/phones/{id}", 1000)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(patchModel)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Phone with id 1000 does not exist"))
                .andExpect(jsonPath("$.status").value(404));
    }
}
