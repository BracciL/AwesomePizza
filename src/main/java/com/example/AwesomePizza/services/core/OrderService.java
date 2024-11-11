package com.example.AwesomePizza.services.core;

import com.example.AwesomePizza.dtos.classes.OrderDTO;
import com.example.AwesomePizza.dtos.classes.OrderStatusDTO;
import com.example.AwesomePizza.dtos.classes.PizzaDTO;
import com.example.AwesomePizza.models.associative.PizzeOrder;
import com.example.AwesomePizza.models.core.Order;
import com.example.AwesomePizza.models.core.Pizza;
import com.example.AwesomePizza.models.typology.OrderStatus;
import com.example.AwesomePizza.repository.associative.PizzeOrderRepository;
import com.example.AwesomePizza.repository.core.OrderRepository;
import com.example.AwesomePizza.repository.core.PizzaRepository;
import com.example.AwesomePizza.repository.typology.OrderStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzeOrderRepository pizzeOrderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    public Order createOrder(OrderDTO orderDTO) {
        String orderCode = UUID.randomUUID().toString().replace("-", "").substring(0, 7).toUpperCase();
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(1);
        Order order = new Order();
        order.setCode(orderCode);
        order.setStatus(orderStatus);
        order.setDescription(orderDTO.getDescription());
        order.setDatetime(orderDTO.getDatetime());
        order.setNote(orderDTO.getNote());
        Order savedOrder = orderRepository.save(order);
        List<PizzeOrder> pizzeOrders = new ArrayList<>();
        for (int i = 0; i < orderDTO.getPizzas().size(); i++) {
            PizzaDTO pizzaDTO = orderDTO.getPizzas().get(i);
            Integer pizzaId = pizzaDTO.getId();
            Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);
            if (pizza.isPresent()) {
                PizzeOrder pizzeOrder = new PizzeOrder();
                pizzeOrder.setOrder(savedOrder);
                pizzeOrder.setPizza(pizza.get());
                pizzeOrders.add(pizzeOrder);
            }
        }
        if (!pizzeOrders.isEmpty()) {
            pizzeOrderRepository.saveAll(pizzeOrders);
        }
        return savedOrder;
    }

    public Optional<OrderDTO> getOrderById(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return Optional.of(convertToOrderDTO(order.get()));
        }
        return Optional.empty();
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for (Order order : orders) {
            orderDTOs.add(convertToOrderDTO(order));
        }

        return orderDTOs;
    }
    public void deleteOrder(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.deleteById(id);
            pizzeOrderRepository.deletePizzeOrdersByOrder_Id(id);

        } else {
            throw new IllegalArgumentException("Order not found with ID: " + id);
        }
    }
    public String takeOrderInCharge(Integer id) {
        if(!orderRepository.existsOrderByStatusId(2))
        {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus = orderStatusRepository.findById(2).orElse(null);

            orderRepository.updateStatus(orderStatus,id);
            return "successo";
        }
        else
        {
            return "non puoi prendere in carico piu' di un ordine alla volta";
        }

    }
    public String updateOrderStatus(OrderStatus orderStatus, Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            orderRepository.updateStatus(orderStatus, id);
            System.out.println("superato updaterepository");
            return "successo";
        } else {
            return "Ordine non trovato con ID: " + id;
        }
    }


    public String getOrderStatus(String orderCode) {
        Order order = orderRepository.findByCode(orderCode);
        if (order != null && order.getStatus() != null) {
            return order.getStatus().getStatus();
        } else {
            return "nessun codice trovato";
        }
    }




    private OrderDTO convertToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCode(order.getCode());
        orderDTO.setDescription(order.getDescription());
        orderDTO.setDatetime(order.getDatetime());
        orderDTO.setNote(order.getNote());

        List<PizzaDTO> pizzaDTOs = new ArrayList<>();
        for (PizzeOrder pizzeOrder : order.getPizzeOrders()) {
            PizzaDTO pizzaDTO = new PizzaDTO();
            pizzaDTO.setName(pizzeOrder.getPizza().getName());
            pizzaDTO.setDescription(pizzeOrder.getPizza().getDescription());
            pizzaDTO.setNote(pizzeOrder.getPizza().getNote());
            pizzaDTOs.add(pizzaDTO);
        }

        orderDTO.setPizzas(pizzaDTOs);

        return orderDTO;
    }



}
