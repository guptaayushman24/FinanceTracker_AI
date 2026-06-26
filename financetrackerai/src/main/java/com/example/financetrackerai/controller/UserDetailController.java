package com.example.financetrackerai.controller;

import com.example.financetrackerai.dto.EmailResponsedto;
import com.example.financetrackerai.dto.UserDetailResponseDTO;
import com.example.financetrackerai.repository.UserRepository;
import com.example.financetrackerai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserDetailController {
    @Autowired
    UserService userService;
    @GetMapping("/alluser")
    public List<UserDetailResponseDTO> userDetailResponseDTOList (){
        return userService.userDetail();
    }

    @GetMapping("/user/{id}")
    public EmailResponsedto findById (@PathVariable("id") Integer id){
        return userService.findById(id);
    }
}
