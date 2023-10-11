    package com.booleanuk.api.cinema.dto;

    import jakarta.validation.constraints.*;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDateTime;

    @Data
    @NoArgsConstructor
    public class CustomerDTO {

        private int id;

        @NotNull
        @NotBlank(message = "Name must not be blank")
        private String name;

        @Email(message = "The email address must be a valid e-mail")
        @NotNull
        @NotBlank(message = "Email must not be blank")
        private String email;

        @NotNull
        @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be a 10-digit number")
        @NotBlank(message = "Phone number must not be blank")
        private String phone;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public CustomerDTO(int id, String name, String email, String phone, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }
