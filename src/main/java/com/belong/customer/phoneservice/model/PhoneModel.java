package com.belong.customer.phoneservice.model;

public class PhoneModel {

    private final Long id;

    private final Long customerId;

    private final String number;

    private final String status;

    public PhoneModel(Long id, Long customerId, String number, String status) {
        this.id = id;
        this.customerId = customerId;
        this.number = number;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getNumber() {
        return number;
    }

    public String getStatus() {
        return status;
    }
}
