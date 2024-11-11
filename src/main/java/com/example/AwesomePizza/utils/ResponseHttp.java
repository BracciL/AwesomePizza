package com.example.AwesomePizza.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHttp {
    private Object dataSource;
    private String message;
    private String code;
}