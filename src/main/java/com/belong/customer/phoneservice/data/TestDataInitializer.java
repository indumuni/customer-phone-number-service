package com.belong.customer.phoneservice.data;

import com.belong.customer.phoneservice.domain.Phone;
import com.belong.customer.phoneservice.repository.PhoneRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestDataInitializer {

    private final PhoneRepository phoneRepository;

    public TestDataInitializer(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @PostConstruct
    public void loadTestData() {
        phoneRepository.deleteAll();

        List<Phone> phones = List.of(
                new Phone(3217L, "+1234567890", "active"),
                new Phone(8271L, "+9876543210", "inactive"),
                new Phone(8271L, "+1122334455", "suspended"),
                new Phone(1925L, "+9988776655", "active"),
                new Phone(4032L, "+5544332211", "inactive"));

        phoneRepository.saveAll(phones);
        System.out.println("Test data initialized!");
    }
}
