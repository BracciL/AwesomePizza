package com.example.AwesomePizza.controllers.core;

import com.example.AwesomePizza.dtos.classes.OrderDTO;
import com.example.AwesomePizza.models.core.Order;
import com.example.AwesomePizza.models.typology.OrderStatus;
import com.example.AwesomePizza.services.core.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderDTO orderDTO;
    private OrderStatus orderStatus;

    @BeforeEach
    void setup() {
        orderDTO = new OrderDTO();
        orderDTO.setId(1);
        orderDTO.setDescription("New Order");

        orderStatus = new OrderStatus();
        orderStatus.setId(3);
        orderStatus.setStatus("Spedito");
        orderStatus.setNote(" ");
    }

    @Test
    void createOrder() throws Exception {
        Order createdOrder = new Order();
        createdOrder.setId(1);
        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(createdOrder);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(orderDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/get-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getOrderById_Found() throws Exception {
        when(orderService.getOrderById(1)).thenReturn(Optional.of(orderDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/find-by-id/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getOrderById_NotFound() throws Exception {
        when(orderService.getOrderById(1)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/find-by-id/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteOrder_Success() throws Exception {
        when(orderService.getOrderById(1)).thenReturn(Optional.of(orderDTO));
        doNothing().when(orderService).deleteOrder(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/orders/delete/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteOrder_NotFound() throws Exception {
        when(orderService.getOrderById(1)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/orders/delete/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void takeOrderInCharge_Success() throws Exception {
        String successMessage = "Ordine preso in carico con successo";
        when(orderService.takeOrderInCharge(1)).thenReturn(successMessage);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/take-in-charge/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
    }

    @Test
    void takeOrderInCharge_Fail() throws Exception {
        String failMessage = "Ordine già preso in carico";
        when(orderService.takeOrderInCharge(1)).thenReturn(failMessage);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/take-in-charge/{id}", 1))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(failMessage));
    }

    @Test
    void updateOrderStatus_Success() throws Exception {
        orderStatus.setStatus("Completed");
        String successMessage = "Stato ordine aggiornato con successo";

        when(orderService.updateOrderStatus(any(OrderStatus.class), eq(1))).thenReturn(successMessage);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/update-order-status/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderStatus)))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
    }

    @Test
    void updateOrderStatus_OrderStatusNull() throws Exception {
        String errorMessage = "Lo stato dell'ordine non può essere null";

        String emptyOrderStatusJson = "{\"id\": null, \"status\": null, \"note\": null}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/update-order-status/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyOrderStatusJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }



    @Test
    void getStatusByCode_Success() throws Exception {
        String code = "ORD123";
        String statusMessage = "Order Status: Completed";
        when(orderService.getOrderStatus(code)).thenReturn(statusMessage);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/get-status/{code}", code))
                .andExpect(status().isOk())
                .andExpect(content().string(statusMessage));
    }

    @Test
    void getStatusByCode_Fail() throws Exception {
        String code = "ORD123";
        when(orderService.getOrderStatus(code)).thenThrow(new IllegalArgumentException());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/get-status/{code}", code))
                .andExpect(status().isBadRequest());
    }
}
