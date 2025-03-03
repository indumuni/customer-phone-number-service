package com.belong.customer.phoneservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneResultsModel {

    private final int pageNo;

    private final long total;

    private final List<PhoneModel> content;

    public PhoneResultsModel(int pageNo, long total, List<PhoneModel> content) {
        this.pageNo = pageNo;
        this.total = total;
        this.content = content;
    }

    public int getPageNo() {
        return pageNo;
    }

    public long getTotal() {
        return total;
    }

    public List<PhoneModel> getContent() {
        return content;
    }
}
