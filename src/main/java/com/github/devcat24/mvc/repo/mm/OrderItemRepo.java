package com.github.devcat24.mvc.repo.mm;

import com.github.devcat24.mvc.entity.mm.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("JPA01OrderItemRepo")
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
