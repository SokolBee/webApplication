package com.sokolov.webApplication.services;
import com.sokolov.webApplication.models.Role;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


public interface RoleService {

    Role getRoleById(long id);
    Role getRoleByName(String name);

    Map<String,Role> getAllRolesAsMap();
    Set<Role> getAllRolesAsSet();

    void addNewRole(Role role);

    void addNewRole(Collection<Role> roles);
    void deleteRole(long id);
    void deleteRole(String name);
    void deleteRole(Collection<Role> roles);
    void deleteAllRoles();






}
