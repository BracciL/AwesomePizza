package com.example.AwesomePizza.services.core;

import com.example.AwesomePizza.dtos.classes.PizzaDTO;
import com.example.AwesomePizza.models.core.Pizza;
import com.example.AwesomePizza.repository.core.PizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PizzaServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @InjectMocks
    private PizzaService pizzaService;

    private Pizza pizza;
    private PizzaDTO pizzaDTO;

    @BeforeEach
    public void setUp() {
        pizza = new Pizza();
        pizza.setName("Margherita");
        pizza.setDescription("Pizza con pomodoro e mozzarella");
        pizza.setNote("Senza basilico");

        pizzaDTO = new PizzaDTO();
        pizzaDTO.setName("Margherita");
        pizzaDTO.setDescription("Pizza con pomodoro e mozzarella");
        pizzaDTO.setNote("Senza basilico");
    }

    @Test
    void testAddPizza() {
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizza);

        Pizza createdPizza = pizzaService.addPizza(pizzaDTO);
        assertNotNull(createdPizza);
        assertEquals("Margherita", createdPizza.getName());
    }

    @Test
    void testUpdatePizza() {
        when(pizzaRepository.findById(1)).thenReturn(Optional.of(pizza));
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizza);

        Pizza updatedPizza = pizzaService.updatePizza(1, pizzaDTO);
        assertNotNull(updatedPizza);
        assertEquals("Margherita", updatedPizza.getName());
    }

    @Test
    void testUpdatePizzaNotFound() {
        when(pizzaRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pizzaService.updatePizza(1, pizzaDTO));
        assertEquals("Pizza non trovata con id: 1", exception.getMessage());
    }

    @Test
    void testFindById() {
        when(pizzaRepository.findById(1)).thenReturn(Optional.of(pizza));

        Optional<Pizza> foundPizza = pizzaService.findById(1);
        assertTrue(foundPizza.isPresent());
        assertEquals("Margherita", foundPizza.get().getName());
    }

    @Test
    void testDeletePizza() {
        doNothing().when(pizzaRepository).deleteById(1);

        pizzaService.deletePizza(1);
        verify(pizzaRepository, times(1)).deleteById(1);
    }
}
