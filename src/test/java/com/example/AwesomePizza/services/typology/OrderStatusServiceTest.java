package com.example.AwesomePizza.services.typology;

import com.example.AwesomePizza.models.typology.OrderStatus;
import com.example.AwesomePizza.repository.typology.OrderStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderStatusServiceTest {

    @Mock
    private OrderStatusRepository orderStatusRepository;

    @InjectMocks
    private OrderStatusService orderStatusService;

    private OrderStatus orderStatus;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderStatus = new OrderStatus();
        orderStatus.setStatus("In Progress");
        orderStatus.setNote("The order is being prepared");
    }

    @Test
    void createOrderStatus() {
        when(orderStatusRepository.save(orderStatus)).thenReturn(orderStatus);

        OrderStatus createdOrderStatus = orderStatusService.createOrderStatus(orderStatus);

        assertNotNull(createdOrderStatus);
        assertEquals("In Progress", createdOrderStatus.getStatus());
        assertEquals("The order is being prepared", createdOrderStatus.getNote());
        verify(orderStatusRepository, times(1)).save(orderStatus);
    }

    @Test
    void getOrderStatusById() {
        when(orderStatusRepository.findById(1)).thenReturn(Optional.of(orderStatus));

        Optional<OrderStatus> foundOrderStatus = orderStatusService.getOrderStatusById(1);

        assertTrue(foundOrderStatus.isPresent());
        assertEquals("In Progress", foundOrderStatus.get().getStatus());
        verify(orderStatusRepository, times(1)).findById(1);
    }

    @Test
    void getAllOrderStatuses() {
        when(orderStatusRepository.findAll()).thenReturn(List.of(orderStatus));

        List<OrderStatus> allOrderStatuses = orderStatusService.getAllOrderStatuses();

        assertFalse(allOrderStatuses.isEmpty());
        assertEquals(1, allOrderStatuses.size());
        verify(orderStatusRepository, times(1)).findAll();
    }

    @Test
    void deleteOrderStatus() {
        doNothing().when(orderStatusRepository).deleteById(1);

        orderStatusService.deleteOrderStatus(1);

        verify(orderStatusRepository, times(1)).deleteById(1);
    }

    @Test
    void updateOrderStatus() {
        OrderStatus updatedStatus = new OrderStatus();
        updatedStatus.setStatus("Completed");
        updatedStatus.setNote("The order has been delivered");

        when(orderStatusRepository.findById(1)).thenReturn(Optional.of(orderStatus));
        when(orderStatusRepository.save(any(OrderStatus.class))).thenReturn(updatedStatus);

        OrderStatus result = orderStatusService.updateOrderStatus(1, updatedStatus);

        assertNotNull(result);
        assertEquals("Completed", result.getStatus());
        assertEquals("The order has been delivered", result.getNote());
        verify(orderStatusRepository, times(1)).findById(1);
        verify(orderStatusRepository, times(1)).save(any(OrderStatus.class));
    }
}
