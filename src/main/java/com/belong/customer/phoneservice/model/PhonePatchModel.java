package com.belong.customer.phoneservice.model;

import com.belong.customer.phoneservice.domain.Status;

public class PhonePatchModel {
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
