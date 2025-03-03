package com.belong.customer.phoneservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PhoneResultsModel(int pageNo, long total, List<PhoneModel> content) {
}
