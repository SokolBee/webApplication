package com.sokolov.webApplication.repository;

import com.sokolov.webApplication.exeption.NotFoundEntityException;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public class RepositoryImpl<T, K> implements
        com.sokolov.webApplication.repository.Repository<T, K> {
    @PersistenceContext
    protected EntityManager em;

    public RepositoryImpl() {
    }

    @Override
    public T selectById(Class<T> tClass, K key) {
        T t = em.find(tClass, key);
        if (t == null) throw new NotFoundEntityException();
        return t;
    }

    @Override
    public void insert(@NotNull T t) {
        em.persist(t);
    }

    @Override
    public void insert(Collection<T> collection) {
        collection.forEach(em::persist);
    }

    @Override
    public void update(@NotNull T t) {
        em.merge(t);
    }

    @Override
    public void update(Collection<T> collection) {
        collection.forEach(em::merge);
    }

    @Override
    public void delete(Class<T> tClass, K key) {
        T t = em.find(tClass, key);
        if (t == null) throw new NotFoundEntityException();
        em.remove(t);
    }

    @Override
    public void delete(Collection<T> collection) {
        collection.forEach(em::remove);
    }

    @Override
    public void deleteAll(Class<T>tClass) {
        CriteriaQuery<T> criteria = em.getCriteriaBuilder()
                .createQuery(tClass);
        em.createQuery(criteria.select(criteria.from(tClass)))
                .getResultList()
                .forEach(em::remove);
    }

    @Override
    public List<T> selectAll(Class<T> tClass) {
        CriteriaQuery<T> criteria = em.getCriteriaBuilder()
                .createQuery(tClass);
        return em.createQuery(criteria.select(criteria.from(tClass)))
                .getResultList();
    }

    @Override
    public List<T> selectByQuery(TypedQuery<T> query) {
        List<T> result = query.getResultList();
        if (result.isEmpty()) throw new NotFoundEntityException();
        else return result;
    }
}
