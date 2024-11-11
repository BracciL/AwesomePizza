package com.example.AwesomePizza.repository.core;

import com.example.AwesomePizza.models.core.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
}