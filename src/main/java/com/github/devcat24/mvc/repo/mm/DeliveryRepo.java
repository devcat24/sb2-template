package com.github.devcat24.mvc.repo.mm;

import com.github.devcat24.mvc.entity.mm.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("JPA01DeliveryRepo")
public interface DeliveryRepo extends JpaRepository<Delivery, Long> {

}
