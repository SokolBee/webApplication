package com.sokolov.webApplication.utils;

import com.sokolov.webApplication.models.Role;
import com.sokolov.webApplication.soap.generated.classes.UserRole;


import java.util.Collection;
import java.util.List;


public class RoleConverter {

    public static List<UserRole> convertRolesToSoap(Collection<Role> collection) {
        return collection.stream()
                .map(RoleConverter::convertRoleTo)
                .toList();
    }
    private static UserRole convertRoleTo(Role role){
        UserRole userRole = new UserRole();
        userRole.setId(role.getId());
        userRole.setName(role.getName());
        return userRole;
    }
    public static List<Role> convertRolesFromSoap(Collection<UserRole> collection) {
        return collection.stream()
                .map(RoleConverter::convertRoleFrom)
                .toList();
    }
    private static Role convertRoleFrom(UserRole userRole){
        return new Role(userRole.getId(),userRole.getName());
    }

}
