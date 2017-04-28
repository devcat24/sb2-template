package com.github.devcat24.mvc.svc.db.repo.mm;

import com.github.devcat24.mvc.svc.db.entity.mm.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("JPA01DeliveryRepo")
public interface DeliveryRepo extends JpaRepository<Delivery, Long> {

}
