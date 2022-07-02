package com.sokolov.webApplication.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User implements Serializable {
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

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
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
