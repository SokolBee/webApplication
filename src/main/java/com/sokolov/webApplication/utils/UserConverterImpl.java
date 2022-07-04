package com.sokolov.webApplication.utils;

import com.sokolov.webApplication.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceUnitUtil;
import java.util.HashSet;

@Component
public class UserConverterImpl implements Converter<User, com.sokolov.webApplication.soap.generated.classes.User> {

    protected final PersistenceUnitUtil util;

    @Autowired
    public UserConverterImpl(PersistenceUnitUtil util) {
        this.util = util;
    }

    public com.sokolov.webApplication.soap.generated.classes.User convertToSoap(User user) {
        com.sokolov.webApplication.soap.generated.classes.User soapUser =
                new com.sokolov.webApplication.soap.generated.classes.User();
        soapUser.setLogin(user.getLogin());
        soapUser.setName(user.getName());
        soapUser.setPassword(user.getPassword());

        if (util.isLoaded(user, "roleSet"))
            soapUser.getRoles().addAll(RoleConverter.convertRolesToSoap(user.getRoleSet()));

        return soapUser;
    }

    public User convertFromSoap(com.sokolov.webApplication.soap.generated.classes.User soapUser) {
        User user = User.builder()
                .setLogin(soapUser.getLogin())
                .setName(soapUser.getName())
                .setPassword(soapUser.getPassword())
                .build();
        if (soapUser.getRoles() != null && soapUser.getRoles().size() > 0) {
            user.setRoleSet(new HashSet<>(RoleConverter.convertRolesFromSoap(soapUser.getRoles())));
        }
        return user;
    }
}
