package com.example.superhero.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private Integer code;
    private String message;
}
