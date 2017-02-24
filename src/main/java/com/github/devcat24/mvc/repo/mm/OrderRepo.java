package com.github.devcat24.mvc.repo.mm;

import com.github.devcat24.mvc.entity.mm.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("JPA01OrderRepo")
public interface OrderRepo extends JpaRepository<Order, Long> {
    Order findById(Long id);
}
