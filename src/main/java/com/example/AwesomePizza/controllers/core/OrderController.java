package com.example.AwesomePizza.controllers.core;

import com.example.AwesomePizza.dtos.classes.OrderDTO;
import com.example.AwesomePizza.models.core.Order;
import com.example.AwesomePizza.models.typology.OrderStatus;
import com.example.AwesomePizza.services.core.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Create a new Order", description = "Endpoint per aggiungere un nuovo Order")
    @PostMapping("/save")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        Order createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(createdOrder);
    }

    @Operation(summary = "Get all Orders", description = "Endpoint per ottenere tutti gli Orders")
    @GetMapping("get-all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get Order by ID", description = "Endpoint per ottenere un Order by ID")
    @GetMapping("find-by-id/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") int id) {
        Optional<OrderDTO> orderDTO = orderService.getOrderById(id);

        if (orderDTO.isPresent()) {
            return ResponseEntity.ok(orderDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete Order by ID", description = "Endpoint per eliminare un Order dato un ID")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") int id) {
        Optional<OrderDTO> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Take order in charge", description = "Endpoint per prendere in carico l'ordine se nessun altro ordine e' stato preso in carico")
    @PutMapping("/take-in-charge/{id}")
    public ResponseEntity<String> takeOrderInCharge(@PathVariable("id")  int id) {
        String responseMessage = orderService.takeOrderInCharge(id);

        if (responseMessage.contains("successo")) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

    @PutMapping("/update-order-status/{id}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable("id") int id, @RequestBody OrderStatus orderStatus) {

        if (orderStatus == null || orderStatus.getId() == null) {
            return ResponseEntity.badRequest().body("Lo stato dell'ordine non può essere null");
        }

        String responseMessage = orderService.updateOrderStatus(orderStatus, id);
        if (responseMessage == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordine non trovato");
        }

        if (responseMessage.contains("successo")) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }




    @Operation(summary = "get status by code", description = "Endpoint che permette di ottenere lo status dal codice dell'order")
    @PutMapping("/get-status/{code}")
    public ResponseEntity<String> getStatusByCode(@PathVariable("code")  String code) {
        {
            try
            {

                return ResponseEntity.ok(orderService.getOrderStatus(code));
            }
            catch(Exception ex)
            {

                return ResponseEntity.badRequest().build();
            }
        }
    }

}