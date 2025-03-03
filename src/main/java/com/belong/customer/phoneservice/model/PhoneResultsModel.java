package com.belong.customer.phoneservice.model;

import java.util.List;

public record PhoneResultsModel(int pageNo, long total, List<PhoneModel> content) {
}
