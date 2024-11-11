package com.example.AwesomePizza.repository.typology;

import com.example.AwesomePizza.models.typology.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {

}