package com.belong.customer.phoneservice.controller;

import com.belong.customer.phoneservice.model.PhoneResultsModel;
import com.belong.customer.phoneservice.service.PhoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.ofNullable;

@RestController
public class PhoneController {

    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("/phones")
    public ResponseEntity<PhoneResultsModel> findPhonesBy(
            @RequestParam(required = false) Long customerId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "100") int pageSize) {

        PhoneResultsModel phones = phoneService.findPhonesBy(ofNullable(customerId), pageNo, pageSize);

        return ResponseEntity.ok(phones);
    }
}
