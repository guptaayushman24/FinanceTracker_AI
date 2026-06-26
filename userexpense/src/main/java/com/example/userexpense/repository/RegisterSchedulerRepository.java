package com.example.userexpense.repository;

import com.example.userexpense.dto.ExpenseDetailSchedulerdto;
import com.example.userexpense.dto.UserDetailResponseDTO;
import com.example.userexpense.dto.UserSchedulerResponsedto;
import com.example.userexpense.model.ExpenseScheduler;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterSchedulerRepository extends JpaRepository<ExpenseScheduler,Integer> {
   @Query(
           """
               select schedulerEvent from ExpenseScheduler where user_id = :userId
               """
   )
    public UserSchedulerResponsedto fetchUserSchedulerDuration (@Param("userId") Integer userId);

   @Modifying
    @Transactional
    @Query(
            """
                    DELETE FROM ExpenseScheduler where user_id = :userId
                    """
    )
    public int deleteScheduledEvent(Integer userId);
}
