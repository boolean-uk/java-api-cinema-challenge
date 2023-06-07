package com.booleanuk.api.cinema.model.dto;

import java.time.LocalDateTime;

public record CustomerResponse (
        String status,
        CustomerData data){
}
