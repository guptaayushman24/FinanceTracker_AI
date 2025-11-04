package com.example.financetrackerai.repository;

import com.example.financetrackerai.dto.UserResponsedto;
import com.example.financetrackerai.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {
    UserModel findByEmail(String email);
}
