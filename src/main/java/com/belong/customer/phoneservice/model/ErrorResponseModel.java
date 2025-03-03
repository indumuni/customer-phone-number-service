package com.belong.customer.phoneservice.model;

import java.time.LocalDateTime;

public record ErrorResponseModel(String error, int status, LocalDateTime timestamp, String path) {
}

