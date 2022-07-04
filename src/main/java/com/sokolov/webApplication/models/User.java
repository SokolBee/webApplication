package com.sokolov.webApplication.models;

import com.sokolov.webApplication.utils.EmbeddedUserBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Entity
@Table(name = "USERS")
public class User implements Serializable, EmbeddedUserBuilder<User,Role>  {
    @Id
    @Column(name = "USER_LOGIN")
    protected String login;

    @NotEmpty
    @Column(name = "USER_NAME", nullable = false)
    protected String name;

    @NotEmpty
    @Pattern(regexp ="(?=.*\\d)(?=.*[A-Z])",message = "password должен содержать букву в заглавном регистре и цифру")
    @Column(name = "USER_PASSWORD" , columnDefinition =
            "varchar(30) not null " +
                    " check (USER_PASSWORD ~ '(?=.*\\d)(?=.*[A-Z])')"
    )
    protected String password;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    protected Set<Role> roleSet;

    public User(){
    }

    public User(String login, String name, String password, Role... roles) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.roleSet = new HashSet<>(Arrays.asList(roles));
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }


    @Override
    public EmbeddedUserBuilder<User, Role> setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    public EmbeddedUserBuilder<User, Role> setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EmbeddedUserBuilder<User, Role> setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public EmbeddedUserBuilder<User, Role> setRoleSet(Collection<Role> collection) {
        if(roleSet == null) this.roleSet = new HashSet<>(collection);
        else roleSet.addAll(collection);
        return this;
    }

    @Override
    public EmbeddedUserBuilder<User, Role> setRoleSet(Role role) {
        if(roleSet == null) this.roleSet = new HashSet<>(){{add(role);}};
        else roleSet.add(role);
        return this;
    }

    @Override
    public User build() {
        return this;
    }

    public static EmbeddedUserBuilder<User,Role> builder()  {
        try {
            return User.class.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(name, user.name) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, name, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", roleSet=" + roleSet +
                '}';
    }
}
