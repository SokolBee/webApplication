package com.sokolov.webApplication.utils;

import com.sokolov.webApplication.models.Role;
import com.sokolov.webApplication.models.User;
import com.sokolov.webApplication.services.RoleService;
import com.sokolov.webApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;



@Component
public class DummyObjectFactory {

    protected final RoleService roleService;
    protected final UserService userService;

    @Autowired
    public DummyObjectFactory(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    public void createData() {
        List<Role> roles = createRolesAndPersist();
        List<User> users = creatUsers();
        userService.addNewUser(bindingUsersAndRoles(users,roles));
    }

    protected List<Role> createRolesAndPersist() {
        List<Role> roleList = Stream.iterate(new Role("r")
                        , role -> true, role -> new Role(role.getName() + role.getName().length() + 1))
                .limit(10)
                .toList();
        roleService.addNewRole(roleList);
        return roleList;
    }

    protected List<User> creatUsers() {
        return Stream.iterate(new User("login", "name", "P3"),
                        user -> true,
                        user -> new User(user.getLogin() + user.getLogin().length() + 1,
                                user.getName() + user.getName().length() + 1,
                                user.getPassword() + user.getPassword().length() + 1))
                .limit(10)
                .toList();
    }

    protected List<User> bindingUsersAndRoles(final List<User> users, final List<Role> roles) {
        return users.stream()
                .peek(user -> {
                    Set<Role> roleSet = new HashSet<>();
                    switch (ThreadLocalRandom.current().nextInt(3)) {
                        case 0 -> {
                            roleSet.add(roles.get(ThreadLocalRandom.current().nextInt(10)));
                            roleSet.add(roles.get(ThreadLocalRandom.current().nextInt(10)));
                            roleSet.add(roles.get(ThreadLocalRandom.current().nextInt(10)));
                        }
                        case 1 -> {
                            roleSet.add(roles.get(ThreadLocalRandom.current().nextInt(10)));
                            roleSet.add(roles.get(ThreadLocalRandom.current().nextInt(10)));
                        }
                        case 2 -> {
                            roleSet.add(roles.get(ThreadLocalRandom.current().nextInt(10)));
                        }
                    }
                    user.setRoleSet(roleSet);
                }).toList();
    }
}
