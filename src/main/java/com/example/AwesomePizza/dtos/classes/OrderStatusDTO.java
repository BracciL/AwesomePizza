package com.example.AwesomePizza.dtos.classes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusDTO {
    private Integer id;
    private String status;
    private String note;
}
