package com.sokolov.webApplication.services;

import com.sokolov.webApplication.exeption.NotFoundEntityException;
import com.sokolov.webApplication.models.Role;
import com.sokolov.webApplication.models.User;
import com.sokolov.webApplication.repository.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    public UserService userService;

    @Autowired
    private Repository<User, String> userRepository;

    @Autowired
    private Repository<Role, Long> roleRepository;

    private User user;


    @BeforeEach
    public void iniTestData() {
        user = User.builder()
                .setLogin("Admin")
                .setName("Vasia")
                .setPassword("QWERTY5")
                .setRoleSet(new Role("Manager"))
                .build();
        userService.addNewUser(user);

    }

    @AfterEach
    public void destroyTestData() {
        userRepository.deleteAll(User.class);
        roleRepository.deleteAll(Role.class);
    }


    @Test
    public void testAddNewUserMethod() {
        user = User.builder()
                .setLogin("Excalibur")
                .setName("Petia")
                .setPassword("SuperPas1234")
                .build();
        assertThrows(NotFoundEntityException.class,
                () -> userService.getUserByLoginWithRoles(user.getLogin()));

        userService.addNewUser(user);

        assertEquals(user,userService.getUserByLoginWithRoles(user.getLogin()));
    }

}
