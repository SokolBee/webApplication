package com.sokolov.webApplication.services;

import com.sokolov.webApplication.models.Role;
import com.sokolov.webApplication.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RolesServiceImpl implements RoleService {

    @PersistenceContext
    protected EntityManager em;

    protected Repository<Role, Long> roleRepository;

    @Autowired
    public RolesServiceImpl(Repository<Role, Long> roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleById(long id) {
        return roleRepository.selectById(Role.class, id);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.selectByQuery(
                        em.createQuery("select r from Role r " +
                                        "where r.name = :name", Role.class)
                                .setParameter("name", name))
                .get(0);
    }

    @Override
    public Map<String, Role> getAllRolesAsMap() {
        return roleRepository.selectAll(Role.class)
                .stream()
                .collect(Collectors
                        .toMap(Role::getName,
                                Function.identity()));
    }

    @Override
    public Set<Role> getAllRolesAsSet() {
      return new HashSet<>(roleRepository.selectAll(Role.class));
    }

    @Override
    public void addNewRole(Role role) {
        roleRepository.insert(role);
    }

    @Override
    public void addNewRole(Collection<Role> roles) {
        roleRepository.insert(roles);
    }

    @Override
    public void deleteRole(long id) {
        roleRepository.delete(Role.class,id);
    }

    @Override
    public void deleteRole(String name) {
        roleRepository.delete(Role.class,getRoleByName(name).getId());
    }

    @Override
    public void deleteRole(Collection<Role> roles) {
        roleRepository.delete(roles);
    }

    @Override
    public void deleteAllRoles() {
        roleRepository.deleteAll(Role.class);
    }
}
