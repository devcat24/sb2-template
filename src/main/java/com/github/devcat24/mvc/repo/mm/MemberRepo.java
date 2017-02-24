package com.github.devcat24.mvc.repo.mm;

import com.github.devcat24.mvc.entity.mm.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("MemberRepo")
public interface MemberRepo extends JpaRepository<Member, Long> {
    Member findById(Long id);
}
