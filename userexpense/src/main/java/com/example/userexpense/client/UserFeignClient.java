package com.example.userexpense.client;

import com.example.userexpense.dto.EmailResponsedto;
import com.example.userexpense.dto.UserResponsedto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "financetrackerai", url = "http://localhost:8080")
public interface UserFeignClient {
    @GetMapping("/alluser")
    List<UserResponsedto> fetchAllUser();

    @GetMapping("/user/{id}")
    EmailResponsedto findById(@PathVariable("id") Integer id);
}