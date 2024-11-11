package com.example.AwesomePizza.controllers.core;

import com.example.AwesomePizza.dtos.classes.PizzaDTO;
import com.example.AwesomePizza.models.core.Pizza;
import com.example.AwesomePizza.services.core.PizzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PizzaController.class)
public class PizzaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    void testAddPizza() throws Exception {
        when(pizzaService.addPizza(any(PizzaDTO.class))).thenReturn(pizza);

        mockMvc.perform(post("/api/pizza/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Margherita\",\"description\":\"Pizza con pomodoro e mozzarella\",\"note\":\"Senza basilico\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Margherita"))
                .andExpect(jsonPath("$.description").value("Pizza con pomodoro e mozzarella"));
    }

    @Test
    void testUpdatePizza() throws Exception {
        when(pizzaService.updatePizza(eq(1), any(PizzaDTO.class))).thenReturn(pizza);

        mockMvc.perform(put("/api/pizza/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Margherita\",\"description\":\"Pizza con pomodoro e mozzarella\",\"note\":\"Senza basilico\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Margherita"))
                .andExpect(jsonPath("$.description").value("Pizza con pomodoro e mozzarella"));
    }

    @Test
    void testGetAllPizzas() throws Exception {
        when(pizzaService.getAllPizzas()).thenReturn(List.of(pizza));

        mockMvc.perform(get("/api/pizza/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Margherita"))
                .andExpect(jsonPath("$[0].description").value("Pizza con pomodoro e mozzarella"));
    }

    @Test
    void testGetPizzaById() throws Exception {
        when(pizzaService.findById(1)).thenReturn(Optional.of(pizza));

        mockMvc.perform(get("/api/pizza/find-by-id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Margherita"))
                .andExpect(jsonPath("$.description").value("Pizza con pomodoro e mozzarella"));
    }

    @Test
    void testGetPizzaByIdNotFound() throws Exception {
        when(pizzaService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/pizza/find-by-id/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletePizza() throws Exception {
        doNothing().when(pizzaService).deletePizza(1);

        mockMvc.perform(delete("/api/pizza/delete/1"))
                .andExpect(status().isNoContent());
    }
}
