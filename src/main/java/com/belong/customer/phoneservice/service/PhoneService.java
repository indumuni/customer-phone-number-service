package com.belong.customer.phoneservice.service;

import com.belong.customer.phoneservice.domain.Phone;
import com.belong.customer.phoneservice.model.PhoneModel;
import com.belong.customer.phoneservice.model.PhoneResultsModel;
import com.belong.customer.phoneservice.repository.PhoneRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Transactional(readOnly = true)
    public PhoneResultsModel findPhonesBy(Optional<Long> customerId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Phone> phoneResults;
        if (customerId.isPresent()) {
            phoneResults = phoneRepository.findByCustomerId(customerId.get(), pageable);
        } else {
            phoneResults = phoneRepository.findAll(pageable);
        }

        return new PhoneResultsModel(
                phoneResults.getNumber(),
                phoneResults.getTotalElements(),
                getPhoneValues(phoneResults.getContent()));
    }

    private List<PhoneModel> getPhoneValues(List<Phone> content) {
        return content.stream()
                .map(p -> new PhoneModel(
                        p.getId(),
                        p.getCustomerId(),
                        p.getNumber(),
                        p.getStatus()))
                .toList();
    }
}
