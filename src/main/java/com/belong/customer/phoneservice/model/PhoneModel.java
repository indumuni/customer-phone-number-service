package com.belong.customer.phoneservice.model;

import com.belong.customer.phoneservice.domain.Status;

public record PhoneModel(Long id, Long customerId, String number, Status status) {
}
