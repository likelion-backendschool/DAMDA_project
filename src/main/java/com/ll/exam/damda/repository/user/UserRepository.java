package com.ll.exam.damda.repository.user;

import com.ll.exam.damda.entity.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {
    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    Optional<Object> findByUsername(String username);
}