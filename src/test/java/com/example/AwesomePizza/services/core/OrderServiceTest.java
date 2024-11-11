package com.example.AwesomePizza.services.core;

import com.example.AwesomePizza.dtos.classes.OrderDTO;
import com.example.AwesomePizza.dtos.classes.PizzaDTO;
import com.example.AwesomePizza.models.associative.PizzeOrder;
import com.example.AwesomePizza.models.core.Order;
import com.example.AwesomePizza.models.core.Pizza;
import com.example.AwesomePizza.models.typology.OrderStatus;
import com.example.AwesomePizza.repository.associative.PizzeOrderRepository;
import com.example.AwesomePizza.repository.core.OrderRepository;
import com.example.AwesomePizza.repository.core.PizzaRepository;
import com.example.AwesomePizza.repository.typology.OrderStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private PizzeOrderRepository pizzeOrderRepository;

    @Mock
    private OrderStatusRepository orderStatusRepository;

    @InjectMocks
    private OrderService orderService;

    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        orderDTO = new OrderDTO();
        orderDTO.setDescription("Test order");
        orderDTO.setDatetime(LocalDateTime.now());
        orderDTO.setNote("Initial note");

        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setId(1);
        pizzaDTO.setName("Margherita");
        pizzaDTO.setDescription("Pomodoro mozzarella");
        pizzaDTO.setNote("Note sulla pizza");

        List<PizzaDTO> pizzas = new ArrayList<>();
        pizzas.add(pizzaDTO);
        orderDTO.setPizzas(pizzas);
    }

    @Test
    void createOrder() {
        when(orderStatusRepository.findById(1)).thenReturn(Optional.of(new OrderStatus()));
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        Pizza pizzaMock = new Pizza();
        pizzaMock.setId(1);
        pizzaMock.setName("Margherita");
        pizzaMock.setDescription("Pomodoro mozzarella");

        when(pizzaRepository.findById(1)).thenReturn(Optional.of(pizzaMock));

        Order createdOrder = orderService.createOrder(orderDTO);

        assertNotNull(createdOrder);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void getOrderById() {
        Order order = new Order();
        order.setId(1);
        order.setCode("ORDER123");

        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Optional<OrderDTO> foundOrder = orderService.getOrderById(1);

        assertTrue(foundOrder.isPresent());
        assertNotNull(foundOrder.get().getCode());
        assertEquals("ORDER123", foundOrder.get().getCode());
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void getAllOrders() {
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setCode("ORDER1");
        Order order2 = new Order();
        order2.setCode("ORDER2");
        orders.add(order1);
        orders.add(order2);

        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderDTO> allOrders = orderService.getAllOrders();

        assertFalse(allOrders.isEmpty());
        assertEquals(2, allOrders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void deleteOrder() {
        Order order = new Order();
        order.setId(1);

        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        orderService.deleteOrder(1);

        verify(orderRepository, times(1)).deleteById(1);
        verify(pizzeOrderRepository, times(1)).deletePizzeOrdersByOrder_Id(1);
    }

    @Test
    void takeOrderInCharge() {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(2);

        when(orderRepository.existsOrderByStatusId(2)).thenReturn(false);
        when(orderStatusRepository.findById(2)).thenReturn(Optional.of(orderStatus));

        String result = orderService.takeOrderInCharge(1);

        assertEquals("successo", result);
        verify(orderRepository, times(1)).updateStatus(orderStatus, 1);
    }

    @Test
    void updateOrderStatus() {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(3);
        Order order = new Order();
        order.setId(1);

        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        String result = orderService.updateOrderStatus(orderStatus, 1);

        assertEquals("successo", result);
        verify(orderRepository, times(1)).updateStatus(orderStatus, 1);
    }

    @Test
    void getOrderStatus() {
        Order order = new Order();
        order.setCode("ORDER123");
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatus("Spedito");
        order.setStatus(orderStatus);

        when(orderRepository.findByCode("ORDER123")).thenReturn(order);

        String status = orderService.getOrderStatus("ORDER123");

        assertEquals("Spedito", status);
        verify(orderRepository, times(1)).findByCode("ORDER123");
    }
}
