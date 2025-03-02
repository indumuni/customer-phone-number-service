package com.belong.customer.phoneservice;

import com.belong.customer.phoneservice.service.PhoneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PhoneServiceApplicationTests {

    @Autowired
    private PhoneService phoneService;

    @Test
    void contextLoads() {
        assertNotNull(phoneService);
    }

}
