package com.example.userexpense.repository.postgres;

import com.example.userexpense.model.UserExpense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExpensePostgresRepo extends JpaRepository<UserExpense, Integer> {
    @Modifying
    @Transactional
    @Query(
            """
                DELETE FROM UserExpense ue WHERE ue.id = :id
                """
    )
    Integer deleUserExpense(
            @Param("id") Integer id
    );
}