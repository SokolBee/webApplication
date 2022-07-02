package com.sokolov.webApplication.utils;

import com.sokolov.webApplication.models.User;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserConverterImpl implements Converter<User,com.sokolov.webApplication.soap.generated.classes.User>  {

    public com.sokolov.webApplication.soap.generated.classes.User convertToSoap(User user) {
        com.sokolov.webApplication.soap.generated.classes.User soapUser =
                new com.sokolov.webApplication.soap.generated.classes.User();
        soapUser.setLogin(user.getLogin());
        soapUser.setName(user.getName());
        soapUser.setPassword(user.getPassword());

        try {
            soapUser.getRoles().addAll(RoleConverter.convertRolesToSoap(user.getRoleSet()));
        }catch (LazyInitializationException e){
            return soapUser;
        }
        return soapUser;
    }

    public User convertFromSoap(com.sokolov.webApplication.soap.generated.classes.User soapUser) {
        User user = new User();
        user.setLogin(soapUser.getLogin());
        user.setName(soapUser.getName());
        user.setPassword(soapUser.getPassword());
        if(soapUser.getRoles() != null && soapUser.getRoles().size() > 0) {
            user.setRoleSet(new HashSet<>(RoleConverter.convertRolesFromSoap(soapUser.getRoles())));
        }
        return user;
    }
}
