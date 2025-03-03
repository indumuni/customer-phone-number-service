package com.belong.customer.phoneservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long customerId;

    @Setter
    private String number;

    @Setter
    private Status status;

    protected Phone() {

    }

    public Phone(Long customerId, String number, Status status) {
        this.customerId = customerId;
        this.number = number;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phone phone)) {
            return false;
        }
        return Objects.equals(id, phone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

