package com.sokolov.webApplication.repository;

import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

public interface Repository<T, K> {

    T selectById(Class<T> tClass, K key);

    void insert(T t);
    void insert(Collection<T> ts);

    void update(T t);
    void update(Collection<T>ts);

    void delete(Class<T> tClass,K key);

    void delete(Collection<T>collection);
    void deleteAll(Class<T> t);

    //fetching users without roles
    List<T> selectAll(Class<T> tClass);

    //fetching particular user with roles
    List<T> selectByQuery(TypedQuery<T> tq);

}
