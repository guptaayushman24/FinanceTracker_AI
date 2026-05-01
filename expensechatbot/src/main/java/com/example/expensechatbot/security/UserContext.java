package com.example.expensechatbot.security;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class UserContext {
    private Integer userId;

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
}