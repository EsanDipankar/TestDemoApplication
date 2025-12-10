package com.example.TestDemoApplication.Repository;

import com.example.TestDemoApplication.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

}
