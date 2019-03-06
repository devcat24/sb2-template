package com.github.devcat24.mvc.db.repo.mm;

import com.github.devcat24.mvc.db.entity.mm.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("JPA01CategoryRepo")
public interface CategoryRepo extends JpaRepository<Category, Long> {

}
