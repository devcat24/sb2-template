package com.github.devcat24.mvc.db.repo.mm;

import com.github.devcat24.mvc.db.entity.mm.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("JPA01OrderItemRepo")
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
