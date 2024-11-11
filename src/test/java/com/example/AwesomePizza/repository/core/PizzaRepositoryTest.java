package com.example.AwesomePizza.repository.core;

import com.example.AwesomePizza.models.core.Pizza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PizzaRepositoryTest {

    @Autowired
    private PizzaRepository pizzaRepository;

    private Pizza pizza;

    @BeforeEach
    public void setUp() {
        pizza = new Pizza();
        pizza.setName("Margherita");
        pizza.setDescription("Pizza con pomodoro e mozzarella");
        pizza.setNote("Senza basilico");
    }

    @Test
    void testSavePizza() {
        Pizza savedPizza = pizzaRepository.save(pizza);
        assertNotNull(savedPizza);
        assertEquals("Margherita", savedPizza.getName());
    }

    @Test
    void testFindById() {
        pizzaRepository.save(pizza);
        Optional<Pizza> foundPizza = pizzaRepository.findById(pizza.getId());
        assertTrue(foundPizza.isPresent());
        assertEquals("Margherita", foundPizza.get().getName());
    }

    @Test
    void testDeletePizza() {
        pizzaRepository.save(pizza);
        pizzaRepository.deleteById(pizza.getId());
        Optional<Pizza> deletedPizza = pizzaRepository.findById(pizza.getId());
        assertFalse(deletedPizza.isPresent());
    }
}
