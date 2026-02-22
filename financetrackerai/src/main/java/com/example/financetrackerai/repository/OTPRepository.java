package com.example.financetrackerai.repository;

import com.example.financetrackerai.dto.DeleteOTPResponsedto;
import com.example.financetrackerai.dto.OTPResponseData;
import com.example.financetrackerai.model.PasswordOTP;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OTPRepository  extends JpaRepository<PasswordOTP,Integer> {
    //PasswordOTP findById (Integer userId);
    @Query("""
            SELECT new com.example.financetrackerai.dto.OTPResponseData(
                po.userId,
                po.otp,
                po.createdAt
            )
            FROM PasswordOTP po
            WHERE po.userId = :userId""")
    OTPResponseData findUserById(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(
            """
               DELETE FROM PasswordOTP po WHERE po.userId = :userId
                    """)
    int deleteUserOTP (@Param("userId") Integer userId);



}
