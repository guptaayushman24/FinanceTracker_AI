package com.example.userexpense.repository;

import com.example.userexpense.model.ExpenseScheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ExpenseSchedulerRepository extends JpaRepository<ExpenseScheduler,Integer> {

       // void save(ExpenseScheduler expenseScheduler);
}
