package com.sokolov.webApplication.utils;

import com.sokolov.webApplication.models.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequestScope
public class UserValidator {

    protected User user;
    protected List<String> message;

    protected Pattern pattern;

    public UserValidator setUser(User user) {
        this.user = user;
        this.message = new ArrayList<>();
        this.pattern = Pattern.compile("(?=.*\\d)(?=.*[A-Z])");
        return this;
    }

    public boolean isValid() {
        if (!(pattern.matcher(user.getPassword()).find()))
            message.add("Password must contain at least one number and an uppercase letter");
        if (user.getName() == null || user.getName().isEmpty())
            message.add("Name can not be empty or null");
        if (user.getLogin() == null || user.getLogin().isEmpty())
            message.add("Login can not be empty or null");
        return message.isEmpty();
    }

    public List<String> getMessage() {
        return message;
    }
}
