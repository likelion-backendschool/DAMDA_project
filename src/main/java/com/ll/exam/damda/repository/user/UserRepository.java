package com.ll.exam.damda.repository.user;

import com.ll.exam.damda.entity.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {
    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    Optional<Object> findByUsername(String username);
    Optional<Object> findByNickname(String nickname);

    @Transactional
    @Modifying
    @Query(value = "truncate site_user", nativeQuery = true)
    void truncate();

    SiteUser findByEmail(String email);
    SiteUser findByUsernameAndEmail(String username, String email);
}