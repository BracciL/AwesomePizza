package com.example.AwesomePizza.services.typology;

import com.example.AwesomePizza.models.typology.OrderStatus;
import com.example.AwesomePizza.repository.typology.OrderStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderStatusService {
    @Autowired
    OrderStatusRepository orderStatusRepository;


    public OrderStatus createOrderStatus(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }

    public Optional<OrderStatus> getOrderStatusById(int id) {
        return orderStatusRepository.findById(id);
    }

    public List<OrderStatus> getAllOrderStatuses() {
        return orderStatusRepository.findAll();
    }

    public void deleteOrderStatus(int id) {
        orderStatusRepository.deleteById(id);
    }

    public OrderStatus updateOrderStatus(int id, OrderStatus orderStatus) {
        Optional<OrderStatus> existingOrderStatus = orderStatusRepository.findById(id);

        if (existingOrderStatus.isPresent()) {
            OrderStatus currentOrderStatus = existingOrderStatus.get();
            currentOrderStatus.setStatus(orderStatus.getStatus());
            currentOrderStatus.setNote(orderStatus.getNote());

            return orderStatusRepository.save(currentOrderStatus);
        } else {
            return null;
        }
    }



}
