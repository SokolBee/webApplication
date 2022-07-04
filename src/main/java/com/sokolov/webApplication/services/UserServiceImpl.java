package com.sokolov.webApplication.services;

import com.sokolov.webApplication.models.Role;
import com.sokolov.webApplication.models.User;
import com.sokolov.webApplication.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    protected final Repository<User, String> userRepository;

    protected final RolesServiceImpl rolesService;

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    public UserServiceImpl(Repository<User, String> userRepository, RolesServiceImpl rolesService) {
        this.userRepository = userRepository;
        this.rolesService = rolesService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.selectAll(User.class);
    }

    @Override
    public User getUserByLoginWithRoles(String login) {
        TypedQuery<User> tq = em.createQuery(
                        "select u from User u left join fetch u.roleSet " +
                                "where u.login = :login", User.class)
                .setParameter("login", login);
        return userRepository.selectByQuery(tq)
                .get(0);
    }

    @Override
    public void deleteUser(String login) {
        userRepository.delete(User.class, login);
    }

    @Override
    public void deleteUser(Collection<User> users) {
        userRepository.delete(users);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll(User.class);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addNewUser(final User user) {
        userRepository.insert(bindingNewRoles(user));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addNewUser(Collection<User> users) {
        users = users.stream()
                .map(this::bindingNewRoles)
                .collect(Collectors.toSet());
        userRepository.insert(users);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateUser(User user) {
        userRepository.update(bindingNewRoles(user));
    }

    @Override
    public User bindingNewRoles(User user) {
        if (user.getRoleSet() != null && user.getRoleSet().size() > 0) {
            final Map<String, Role> map = rolesService.getAllRolesAsMap();
            Set<Role> roles = user.getRoleSet().stream()
                    .filter(role -> !(map.containsKey(role.getName())))
                    .map(role -> role = new Role(role.getName()))
                    .collect(Collectors.toSet());
            rolesService.addNewRole(roles);
            user.getRoleSet().removeIf(roles::contains);
            user.getRoleSet().addAll(roles);
        }
        return user;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateUser(Collection<User> users) {
        users = users.stream()
                .map(this::bindingNewRoles)
                .collect(Collectors.toSet());
        userRepository.update(users);
    }
}
