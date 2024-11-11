package com.example.AwesomePizza.repository.associative;

import com.example.AwesomePizza.models.associative.PizzeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzeOrderRepository extends JpaRepository<PizzeOrder, Integer> {

  void deletePizzeOrdersByOrder_Id(Integer idOrder);


}