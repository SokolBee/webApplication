package com.sokolov.webApplication.services;

import com.sokolov.webApplication.models.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserByLoginWithRoles(String login);
    void deleteUser(String login);
    void addNewUser(User user);
    void addNewUser(Collection<User> users);
    void updateUser(User user);

    User bindingNewRoles(User user);

    void updateUser(Collection<User> users);
 }
