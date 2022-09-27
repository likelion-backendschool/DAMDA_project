package com.ll.exam.damda.repository.search.review;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RepositoryUtil {
    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void disableForeignKeyChecks();

    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void enableForeignKeyChecks();

    void truncate();

    //default메서드 (외부 참조o 외부 수정x) 도입하여 중복을 좀 더 제거한다.
    default void truncateTable() {
        //disableForeignKeyChecks();
        truncate();
        //enableForeignKeyChecks();
    }
}
