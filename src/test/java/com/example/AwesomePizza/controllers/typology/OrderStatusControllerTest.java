package com.example.AwesomePizza.controllers.typology;

import com.example.AwesomePizza.dtos.classes.OrderStatusDTO;
import com.example.AwesomePizza.models.typology.OrderStatus;
import com.example.AwesomePizza.services.typology.OrderStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderStatusControllerTest {

    @Mock
    private OrderStatusService orderStatusService;

    @InjectMocks
    private OrderStatusController orderStatusController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderStatusController).build();
    }

    @Test
    void createOrderStatus() throws Exception {
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setStatus("In Progress");
        orderStatusDTO.setNote("Processing order");

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(1);
        orderStatus.setStatus("In Progress");
        orderStatus.setNote("Processing order");

        when(orderStatusService.createOrderStatus(any(OrderStatus.class))).thenReturn(orderStatus);

        mockMvc.perform(post("/api/order-status/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"In Progress\", \"note\": \"Processing order\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("In Progress"))
                .andExpect(jsonPath("$.note").value("Processing order"));

        verify(orderStatusService, times(1)).createOrderStatus(any(OrderStatus.class));
    }

    @Test
    void updateOrderStatus() throws Exception {
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setStatus("Completed");
        orderStatusDTO.setNote("Order complete");

        OrderStatus updatedOrderStatus = new OrderStatus();
        updatedOrderStatus.setId(1);
        updatedOrderStatus.setStatus("Completed");
        updatedOrderStatus.setNote("Order complete");

        when(orderStatusService.updateOrderStatus(eq(1), any(OrderStatus.class))).thenReturn(updatedOrderStatus);

        mockMvc.perform(put("/api/order-status/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"Completed\", \"note\": \"Order complete\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Completed"))
                .andExpect(jsonPath("$.note").value("Order complete"));

        verify(orderStatusService, times(1)).updateOrderStatus(eq(1), any(OrderStatus.class));
    }

    @Test
    void updateOrderStatus_NotFound() throws Exception {
        when(orderStatusService.updateOrderStatus(eq(1), any(OrderStatus.class))).thenReturn(null);

        mockMvc.perform(put("/api/order-status/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"Completed\", \"note\": \"Order complete\"}"))
                .andExpect(status().isNotFound());

        verify(orderStatusService, times(1)).updateOrderStatus(eq(1), any(OrderStatus.class));
    }

    @Test
    void getAllOrderStatuses() throws Exception {
        OrderStatus orderStatus1 = new OrderStatus();
        orderStatus1.setId(1);
        orderStatus1.setStatus("In Progress");
        orderStatus1.setNote("Processing order");

        OrderStatus orderStatus2 = new OrderStatus();
        orderStatus2.setId(2);
        orderStatus2.setStatus("Completed");
        orderStatus2.setNote("Order complete");

        List<OrderStatus> orderStatuses = new ArrayList<>();
        orderStatuses.add(orderStatus1);
        orderStatuses.add(orderStatus2);

        when(orderStatusService.getAllOrderStatuses()).thenReturn(orderStatuses);

        mockMvc.perform(get("/api/order-status/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("In Progress"))
                .andExpect(jsonPath("$[1].status").value("Completed"));

        verify(orderStatusService, times(1)).getAllOrderStatuses();
    }

    @Test
    void getOrderStatusById() throws Exception {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(1);
        orderStatus.setStatus("In Progress");
        orderStatus.setNote("Processing order");

        when(orderStatusService.getOrderStatusById(1)).thenReturn(Optional.of(orderStatus));

        mockMvc.perform(get("/api/order-status/find-by-id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("In Progress"))
                .andExpect(jsonPath("$.note").value("Processing order"));

        verify(orderStatusService, times(1)).getOrderStatusById(1);
    }

    @Test
    void getOrderStatusById_NotFound() throws Exception {
        when(orderStatusService.getOrderStatusById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/order-status/find-by-id/1"))
                .andExpect(status().isNotFound());

        verify(orderStatusService, times(1)).getOrderStatusById(1);
    }

    @Test
    void deleteOrderStatus() throws Exception {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(1);
        orderStatus.setStatus("Completed");
        orderStatus.setNote("Order complete");

        when(orderStatusService.getOrderStatusById(1)).thenReturn(Optional.of(orderStatus));
        doNothing().when(orderStatusService).deleteOrderStatus(1);

        mockMvc.perform(delete("/api/order-status/delete/1"))
                .andExpect(status().isNoContent());

        verify(orderStatusService, times(1)).deleteOrderStatus(1);
    }

    @Test
    void deleteOrderStatus_NotFound() throws Exception {
        when(orderStatusService.getOrderStatusById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/order-status/delete/1"))
                .andExpect(status().isNotFound());

        verify(orderStatusService, never()).deleteOrderStatus(1);
    }
}
