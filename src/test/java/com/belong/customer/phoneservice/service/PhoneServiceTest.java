package com.belong.customer.phoneservice.service;

import com.belong.customer.phoneservice.domain.Phone;
import com.belong.customer.phoneservice.model.PhoneModel;
import com.belong.customer.phoneservice.model.PhoneResultsModel;
import com.belong.customer.phoneservice.repository.PhoneRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PhoneServiceTest {

    @Mock
    private PhoneRepository mockPhoneRepository;

    @Mock
    private Page<Phone> mockPage;

    @InjectMocks
    private PhoneService phoneService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void findPhone_shouldReturnPageDetails_givenEmptyCustomer() {
        mockPhoneRepositoryFindAll(withAllCustomerPhones());

        PhoneResultsModel phonesBy = phoneService.findPhonesBy(Optional.empty(), 0, 100);

        assertEquals(0, phonesBy.pageNo());
        assertEquals(5, phonesBy.total());
    }

    @Test
    public void findPhone_shouldReturnAllPhoneNumbers_givenEmptyCustomer() {
        mockPhoneRepositoryFindAll(withAllCustomerPhones());

        PhoneResultsModel phonesBy = phoneService.findPhonesBy(Optional.empty(), 0, 100);

        assertEquals(5, phonesBy.content().size());
        assertEquals("[+1234567890, +9876543210, +1122334455, +9988776655, +5544332211]",
                phonesBy.content().stream().map(PhoneModel::number).toList().toString());
    }

    @Test
    public void findPhone_shouldReturnCustomerPhoneNumbers_givenFindByCustomer() {
        mockPhoneRepositoryFindByCustomer(withSpecificCustomerPhones());

        PhoneResultsModel phonesBy = phoneService.findPhonesBy(Optional.of(8271L), 0, 100);

        assertEquals(2, phonesBy.content().size());
        assertEquals("[+9876543210, +1122334455]",
                phonesBy.content().stream().map(PhoneModel::number).toList().toString());
    }

    @Test
    public void patchPhone_shouldSavePhoneWithNewStatus_givenValidRequest() {

        Optional<Phone> findPhone = Optional.of(new Phone(3217L, "+1234567890", "active"));
        when(mockPhoneRepository.findById(anyLong())).thenReturn(findPhone);

        Phone savedPhone = new Phone(3217L, "+1234567890", "suspended");
        when(mockPhoneRepository.save(any(Phone.class))).thenReturn(savedPhone);

        PhoneModel model = phoneService.updatePhoneStatus(3217L, "suspended");

        assertEquals("+1234567890", model.number());
        assertEquals("suspended", model.status());

        verify(mockPhoneRepository).findById(3217L);
        verify(mockPhoneRepository).save(any(Phone.class));
    }

    private void mockPhoneRepositoryFindByCustomer(List<Phone> customerPhones) {
        when(mockPage.getTotalElements()).thenReturn((long) customerPhones.size());
        when(mockPage.getNumber()).thenReturn(0);
        when(mockPage.getSize()).thenReturn(100);

        when(mockPage.getContent()).thenReturn(customerPhones);
        when(mockPhoneRepository.findByCustomerId(anyLong(), any(Pageable.class))).thenReturn(mockPage);
    }

    private void mockPhoneRepositoryFindAll(List<Phone> customerPhones) {
        when(mockPage.getTotalElements()).thenReturn((long) customerPhones.size());
        when(mockPage.getNumber()).thenReturn(0);
        when(mockPage.getSize()).thenReturn(100);

        when(mockPage.getContent()).thenReturn(customerPhones);
        when(mockPhoneRepository.findAll(any(Pageable.class))).thenReturn(mockPage);
    }

    private static List<Phone> withAllCustomerPhones() {
        return List.of(
        new Phone(3217L, "+1234567890", "active"),
        new Phone(8271L, "+9876543210", "inactive"),
        new Phone(8271L, "+1122334455", "suspended"),
        new Phone(1925L, "+9988776655", "active"),
        new Phone(4032L, "+5544332211", "inactive"));
    }

    private static List<Phone> withSpecificCustomerPhones() {
        List<Phone> phoneResults = new ArrayList<>();
        phoneResults.add(new Phone(8271L, "+9876543210", "inactive"));
        phoneResults.add(new Phone(8271L, "+1122334455", "suspended"));
        return phoneResults;
    }
}
