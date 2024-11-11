package com.example.AwesomePizza.models.associative;

import com.example.AwesomePizza.models.core.Order;
import com.example.AwesomePizza.models.core.Pizza;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "as_pizzeorders")
public class PizzeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pizza_orders", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pizza", nullable = false)
    private Pizza pizza;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;

}