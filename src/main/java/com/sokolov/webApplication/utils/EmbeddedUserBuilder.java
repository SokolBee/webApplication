package com.sokolov.webApplication.utils;

import java.util.Collection;

public interface EmbeddedUserBuilder<T extends EmbeddedUserBuilder<T, C>, C> {


    EmbeddedUserBuilder<T, C> setLogin(String login);


    EmbeddedUserBuilder<T, C> setName(String name);


    EmbeddedUserBuilder<T, C> setPassword(String password);


    EmbeddedUserBuilder<T, C> setRoleSet(Collection<C> collection);


    EmbeddedUserBuilder<T, C> setRoleSet(C role);


    T build();

}
