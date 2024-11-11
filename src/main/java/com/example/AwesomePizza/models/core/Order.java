package com.example.AwesomePizza.models.core;

import com.example.AwesomePizza.models.associative.PizzeOrder;
import com.example.AwesomePizza.models.typology.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "fu_orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_status", referencedColumnName = "id_order_status", nullable = false)
    private OrderStatus status;

    @Size(max = 45)
    @Column(name = "code", length = 45)
    private String code;

    @Size(max = 45)
    @Column(name = "description", length = 45)
    private String description;


    @Column(name = "datetime")
    private LocalDateTime datetime;

    @Size(max = 45)
    @Column(name = "note", length = 45)
    private String note;

    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private List<PizzeOrder> pizzeOrders = new ArrayList<>();

}