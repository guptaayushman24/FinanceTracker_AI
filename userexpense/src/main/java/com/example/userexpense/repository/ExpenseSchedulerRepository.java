package com.example.userexpense.repository;

import com.example.userexpense.dto.AllExpenseeResponsedto;
import com.example.userexpense.model.ExpenseScheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface ExpenseSchedulerRepository extends JpaRepository<ExpenseScheduler,Integer> {
    @Query(
            value = """
            SELECT
            DISTINCT
                ue.expense_type,
                ue.value, 
                ue.description,
                pe.payment_mode,
               DATE(ue.expense_date) AS expense_date
            FROM user_expense ue
            JOIN expense_scheduler es ON ue.user_id = es.user_id
            JOIN payment_mode pe      ON ue.user_id = pe.user_id
            WHERE ue.expense_date BETWEEN
                  DATE_SUB(
                      DATE(:expenseDate),
                      INTERVAL (
                          SELECT REGEXP_SUBSTR(scheduler_event, '[0-9]+')
                          FROM expense_scheduler
                          WHERE user_id = :user_id
                      ) DAY
                  )
                  AND DATE_SUB(DATE(:expenseDate), INTERVAL 1 DAY)
            """,
            nativeQuery = true
    )
       List<AllExpenseeResponsedto> expenseRecordScheduler (@Param("user_id") Integer user_id, @Param("expenseDate") LocalDate expenseDate);
}
