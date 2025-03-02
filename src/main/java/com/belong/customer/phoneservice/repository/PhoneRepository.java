package com.belong.customer.phoneservice.repository;

import com.belong.customer.phoneservice.domain.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

    Page<Phone> findByCustomerId(Long customerId, Pageable pageable);
}
