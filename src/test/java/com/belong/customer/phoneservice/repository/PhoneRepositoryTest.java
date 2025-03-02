package com.belong.customer.phoneservice.repository;

import com.belong.customer.phoneservice.domain.Phone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class PhoneRepositoryTest {

    @Autowired
    private PhoneRepository phoneRepository;

    @Test
    public void save_shouldSavePhoneWithId_givenValidCustomerPhoneNumber() {
        Phone phone = new Phone(1122L, "03 9988 3322", "ACTIVE");
        Phone savedPhone = phoneRepository.save(phone);
        assertNotNull(savedPhone.getId());

        phoneRepository.findById(savedPhone.getId()).ifPresent(foundPhone -> {
            assertEquals(1122L, foundPhone.getCustomerId());
            assertEquals("03 9988 3322", foundPhone.getNumber());
            assertEquals("ACTIVE", foundPhone.getStatus());
        });
    }

    @Test
    public void save_shouldFindCustomerPhone_givenValidCustomerPhoneNumber() {
        Phone phone = new Phone(1122L, "03 9988 3322", "ACTIVE");
        Phone savedPhone = phoneRepository.save(phone);
        assertNotNull(savedPhone.getId());

        phoneRepository.findByCustomerId(1122L, PageRequest.of(0, 10))
                .stream()
                .findFirst()
                .ifPresent(foundPhone -> {
                    assertEquals(1122L, foundPhone.getCustomerId());
                    assertEquals("03 9988 3322", foundPhone.getNumber());
                    assertEquals("ACTIVE", foundPhone.getStatus());
                });
    }
}
