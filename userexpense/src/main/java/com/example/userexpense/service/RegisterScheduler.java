package com.example.userexpense.service;

import com.example.userexpense.dto.RegisterSchedulerRequestdto;
import com.example.userexpense.dto.RegisterSchedulerResponsedto;
import com.example.userexpense.dto.UserResponsedto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface RegisterScheduler {
    public RegisterSchedulerResponsedto registerEvent(RegisterSchedulerRequestdto registerSchedulerRequestdto,Integer userId);
    public List<UserResponsedto> fetchAllUser() throws JsonProcessingException;
    public RegisterSchedulerResponsedto deleteRegisterScheduler (Integer userId);

}
