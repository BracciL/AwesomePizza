package com.example.AwesomePizza.models.typology;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ty_order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_status", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "status", length = 45)
    private String status;

    @Size(max = 45)
    @Column(name = "note", length = 45)
    private String note;

}