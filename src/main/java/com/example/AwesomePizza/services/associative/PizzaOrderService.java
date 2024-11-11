package com.example.AwesomePizza.services.associative;

import com.example.AwesomePizza.repository.associative.PizzeOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PizzaOrderService {
    @Autowired
    PizzeOrderRepository pizzaOrderRepository;
}
