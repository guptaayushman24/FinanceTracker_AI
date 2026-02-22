package com.example.financetrackerai.repository;

import com.example.financetrackerai.dto.*;
import com.example.financetrackerai.model.PasswordOTP;
import com.example.financetrackerai.model.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {
    UserModel findByemailAddress(String emailAddress);
    SignupRequestdto save(SignupRequestdto signupRequestdto);
    LoginResponsedto findByEmailAddressAndPassword(String email, String password);
    void save (PasswordOTP passwordOTP);
    @Modifying
    @Transactional
    @Query
            (
                    """
                        update UserModel ue SET ue.password =:password  where ue.id =:userId
                            """
            )
    int updatePassword (@Param("id") Integer userId,@Param("password") String newPassword);
}
