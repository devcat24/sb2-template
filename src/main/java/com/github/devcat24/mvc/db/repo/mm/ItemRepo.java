package com.github.devcat24.mvc.db.repo.mm;

import com.github.devcat24.mvc.db.entity.mm.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("JPA01ItemRepo")
public interface ItemRepo extends JpaRepository<Item, Long> {
}
