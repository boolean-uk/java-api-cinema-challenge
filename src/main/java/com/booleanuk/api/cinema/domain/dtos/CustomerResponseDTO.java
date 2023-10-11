package com.booleanuk.api.cinema.domain.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}