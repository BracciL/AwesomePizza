package com.example.AwesomePizza.controllers.core;

import com.example.AwesomePizza.dtos.classes.PizzaDTO;
import com.example.AwesomePizza.models.core.Pizza;
import com.example.AwesomePizza.services.core.PizzaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pizza")
public class PizzaController {

    @Autowired
    PizzaService pizzaService;

    @Operation(summary = "add Pizza", description = "Endpoint per aggiungere una nuova pizza")
    @PostMapping("/save")
    public ResponseEntity<PizzaDTO> addPizza(@RequestBody PizzaDTO pizzaDTO)
    {
        Pizza createdPizza = pizzaService.addPizza(pizzaDTO);
        PizzaDTO createdPizzaDTO = convertToDTO(createdPizza);
        return ResponseEntity.ok(createdPizzaDTO);
    }

    @Operation(summary = "update Pizza", description = "Endpoint per aggiornare una pizza esistente")
    @PutMapping("/update/{id}")
    public ResponseEntity<PizzaDTO> updatePizza (@PathVariable Integer id,@RequestBody PizzaDTO pizzaDTO)
    {
        Pizza updatedPizza = pizzaService.updatePizza(id,pizzaDTO);
        PizzaDTO updatedPizzaDTO = convertToDTO(updatedPizza);
        return ResponseEntity.ok(updatedPizzaDTO);
    }

    @Operation(summary = "Get-all Pizzas", description = "Endpoint per ottenere tutte le pizze")
    @GetMapping("get-all")
    public ResponseEntity<List<PizzaDTO>> getAllPizzas()
    {
        List<Pizza> pizzas = pizzaService.getAllPizzas();
        List<PizzaDTO> pizzasDTO = new ArrayList<>();
        pizzas.forEach(pizza -> {
            PizzaDTO pizzaDTO = convertToDTO(pizza);
            pizzasDTO.add(pizzaDTO);
        });
        return ResponseEntity.ok(pizzasDTO);
    }
    @Operation(summary = "Find pizza by id", description = "Endpoint per ottenere una pizza dato un ID")
    @GetMapping("find-by-id/{id}")
    public ResponseEntity<PizzaDTO> getPizzaById(@PathVariable Integer id)
    {
        Optional<Pizza> optionalPizza = pizzaService.findById(id);
        if(optionalPizza.isPresent()){
            PizzaDTO pizzaDTO =convertToDTO(optionalPizza.get());
            return ResponseEntity.ok(pizzaDTO);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete pizza by id", description = "Endpoint per eliminare una pizza dato un ID")
    @DeleteMapping("delete/{id}")
    public  ResponseEntity<Void> deletePizza(@PathVariable Integer id)
    {
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }


    private PizzaDTO convertToDTO(Pizza pizza) {
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setName(pizza.getName());
        pizzaDTO.setDescription(pizza.getDescription());
        pizzaDTO.setNote(pizza.getNote());
        return pizzaDTO;
    }
}
