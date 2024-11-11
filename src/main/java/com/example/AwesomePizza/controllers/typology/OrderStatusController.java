package com.example.AwesomePizza.controllers.typology;

import com.example.AwesomePizza.dtos.classes.OrderStatusDTO;
import com.example.AwesomePizza.models.typology.OrderStatus;
import com.example.AwesomePizza.services.typology.OrderStatusService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/order-status")
public class OrderStatusController {

    @Autowired
    private OrderStatusService orderStatusService;

    @Operation(summary = "Create a new OrderStatus", description = "Endpoint per aggiungere un nuovo stato ordine")
    @PostMapping("/save")
    public ResponseEntity<OrderStatusDTO> createOrderStatus(@RequestBody OrderStatusDTO orderStatusDTO) {

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatus(orderStatusDTO.getStatus());
        orderStatus.setNote(orderStatusDTO.getNote());

        OrderStatus savedOrderStatus = orderStatusService.createOrderStatus(orderStatus);

        OrderStatusDTO responseDTO = new OrderStatusDTO();
        responseDTO.setId(savedOrderStatus.getId());
        responseDTO.setStatus(savedOrderStatus.getStatus());
        responseDTO.setNote(savedOrderStatus.getNote());

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @Operation(summary = "Update OrderStatus by ID", description = "Endpoint per fare l'update sull'order status")
    @PutMapping("/update/{id}")
    public ResponseEntity<OrderStatusDTO> updateOrderStatus(@PathVariable("id") int id, @RequestBody OrderStatusDTO orderStatusDTO) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatus(orderStatusDTO.getStatus());
        orderStatus.setNote(orderStatusDTO.getNote());

        OrderStatus updatedOrderStatus = orderStatusService.updateOrderStatus(id, orderStatus);

        if (updatedOrderStatus != null) {
            OrderStatusDTO responseDTO = new OrderStatusDTO();
            responseDTO.setId(updatedOrderStatus.getId());
            responseDTO.setStatus(updatedOrderStatus.getStatus());
            responseDTO.setNote(updatedOrderStatus.getNote());

            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all OrderStatuses", description = "Endpoint per ottenere tutti gli OrderStatus")
    @GetMapping("get-all")
    public ResponseEntity<List<OrderStatusDTO>> getAllOrderStatuses() {
        List<OrderStatus> orderStatuses = orderStatusService.getAllOrderStatuses();
        List<OrderStatusDTO> responseDTOs = new ArrayList<>();

        orderStatuses.forEach(orderStatus -> {
            OrderStatusDTO dto = new OrderStatusDTO();
            dto.setId(orderStatus.getId());
            dto.setStatus(orderStatus.getStatus());
            dto.setNote(orderStatus.getNote());
            responseDTOs.add(dto);
        });

        return ResponseEntity.ok(responseDTOs);
    }

    @Operation(summary = "Get OrderStatus by ID", description = "Endpoint per ottenere l'orderstatus per id")
    @GetMapping("find-by-id/{id}")
    public ResponseEntity<OrderStatusDTO> getOrderStatusById(@PathVariable("id") int id) {
        Optional<OrderStatus> orderStatus = orderStatusService.getOrderStatusById(id);

        if (orderStatus.isPresent()) {
            OrderStatusDTO responseDTO = new OrderStatusDTO();
            responseDTO.setId(orderStatus.get().getId());
            responseDTO.setStatus(orderStatus.get().getStatus());
            responseDTO.setNote(orderStatus.get().getNote());

            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @Operation(summary = "Delete OrderStatus by ID", description = "Endpoint per eliminare un OrderStatus")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteOrderStatus(@PathVariable("id") int id) {

        Optional<OrderStatus> orderStatus = orderStatusService.getOrderStatusById(id);

        if (orderStatus.isPresent()) {
            orderStatusService.deleteOrderStatus(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }





}