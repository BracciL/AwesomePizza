package com.example.AwesomePizza.dtos.classes;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Integer id;
    private String code;
    private String description;
    private LocalDateTime datetime;
    private String note;
    private List<PizzaDTO> pizzas;
}