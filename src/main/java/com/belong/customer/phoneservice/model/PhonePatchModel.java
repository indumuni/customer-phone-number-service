package com.belong.customer.phoneservice.model;

import com.belong.customer.phoneservice.domain.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhonePatchModel {
    private Status status;
}
