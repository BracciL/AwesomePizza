package com.example.AwesomePizza.repository.core;

import com.example.AwesomePizza.models.core.Order;
import com.example.AwesomePizza.models.typology.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Modifying
    @Query("UPDATE Order o SET o.status.id = :#{#orderStatus.id} WHERE o.id = :#{#idOrder}")
    void updateStatus(OrderStatus orderStatus, Integer idOrder);


    boolean existsOrderByStatusId(Integer idStatus);

    Order findByCode (String code);

}