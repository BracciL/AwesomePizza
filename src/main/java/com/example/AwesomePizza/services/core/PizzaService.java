package com.example.AwesomePizza.services.core;

import com.example.AwesomePizza.dtos.classes.PizzaDTO;
import com.example.AwesomePizza.models.core.Pizza;
import com.example.AwesomePizza.repository.core.PizzaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;


    public Pizza addPizza (PizzaDTO pizzaDTO)
    {
        Pizza pizza = new Pizza();
        pizza.setName(pizzaDTO.getName());
        pizza.setDescription(pizzaDTO.getDescription());
        pizza.setNote(pizzaDTO.getNote());
        return pizzaRepository.save(pizza);
    }

    public Pizza updatePizza (Integer idPizza ,PizzaDTO pizzaDTO)
    {
        Optional<Pizza> optionalPizza = pizzaRepository.findById(idPizza);
        if(optionalPizza.isPresent())
        {
            Pizza pizza = optionalPizza.get();
            pizza.setName(pizzaDTO.getName());
            pizza.setDescription(pizzaDTO.getDescription());
            pizza.setNote(pizzaDTO.getNote());
            return pizzaRepository.save(pizza);
        }
        else {
            throw new RuntimeException("Pizza non trovata con id: " + idPizza);
        }
    }

    public List<Pizza> getAllPizzas()
    {
        return pizzaRepository.findAll();
    }

    public Optional<Pizza> findById(Integer idPizza)
    {
        return pizzaRepository.findById(idPizza);
    }

    public void deletePizza (Integer idPizza)
    {
        pizzaRepository.deleteById(idPizza);
    }

}
