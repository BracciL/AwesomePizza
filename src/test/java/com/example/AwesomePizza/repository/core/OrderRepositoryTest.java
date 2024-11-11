package com.example.AwesomePizza.repository.core;

import com.example.AwesomePizza.models.core.Order;
import com.example.AwesomePizza.models.typology.OrderStatus;
import com.example.AwesomePizza.repository.typology.OrderStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;  // Aggiungi questa annotazione
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // Questa annotazione carica il contesto di Spring Boot
@ExtendWith(SpringExtension.class)
@Transactional
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    private Order order;
    private OrderStatus orderStatus;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        orderStatusRepository.deleteAll();
        orderStatus = new OrderStatus();

        orderStatus.setStatus("Spedito");
        orderStatus.setNote(" ");
        orderStatus = orderStatusRepository.save(orderStatus);

        order = new Order();
        order.setStatus(orderStatus);
        order.setCode("ORDER123");
        order.setDescription("Test Order");
        order.setDatetime(LocalDateTime.now());
        order.setNote("Initial note");
        order = orderRepository.save(order);


    }
    @Test
    @Transactional
    void updateStatus() {
        OrderStatus newStatus = new OrderStatus();
        newStatus.setStatus("Spedito");
        newStatus.setNote(" ");
        newStatus = orderStatusRepository.save(newStatus);

        Order orderToUpdate = orderRepository.findById(order.getId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        orderToUpdate.setStatus(newStatus);
        orderRepository.save(orderToUpdate);

        Optional<Order> updatedOrder = orderRepository.findById(order.getId());
        assertTrue(updatedOrder.isPresent(), "L'ordine dovrebbe esistere");
        assertEquals("Spedito", updatedOrder.get().getStatus().getStatus(), "Lo stato dell'ordine non è stato aggiornato correttamente");
    }


    @Test
    void existsOrderByStatusId() {
        boolean exists = orderRepository.existsOrderByStatusId(orderStatus.getId());
        assertTrue(exists);

        boolean nonExistent = orderRepository.existsOrderByStatusId(-1);
        assertFalse(nonExistent);
    }

    @Test
    void findByCode() {
        Order foundOrder = orderRepository.findByCode("ORDER123");
        assertNotNull(foundOrder);
        assertEquals("Test Order", foundOrder.getDescription());
        assertEquals(orderStatus.getId(), foundOrder.getStatus().getId());

        Order nonExistentOrder = orderRepository.findByCode("INVALID_CODE");
        assertNull(nonExistentOrder);
    }

    @Test
    void deleteInBatch() {
        Order order2 = new Order();
        order2.setStatus(orderStatus);
        order2.setCode("ORDER456");
        order2.setDescription("Second Test Order");
        order2.setDatetime(LocalDateTime.now());
        order2.setNote("Second note");
        orderRepository.save(order2);

        orderRepository.deleteInBatch(orderRepository.findAll());

        assertEquals(0, orderRepository.count());
    }
}
