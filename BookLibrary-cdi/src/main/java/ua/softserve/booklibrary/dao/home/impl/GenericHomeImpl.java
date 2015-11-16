package ua.softserve.booklibrary.dao.home.impl;

import ua.softserve.booklibrary.dao.home.GenericHome;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;

public abstract class GenericHomeImpl<T> implements GenericHome<T> {

    @PersistenceContext(unitName = "OracleDS")
    private EntityManager em;

    private final Class<T> entityClass;

    public GenericHomeImpl() {
        this.entityClass = (Class<T>)
                ((ParameterizedType) getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    @Override
    public void save(T entity) {
        em.persist(entity);
    }

    @Override
    public T update(T entity) {
        return em.merge(entity);
    }

    @Override
    public void removeByPk(Long id) {

        Object existEntity = em.getReference(entityClass, id);
        em.remove(existEntity);

//        em.remove(em.contains(entity) ? entity : em.merge(entity));
//        em.remove(entity);
    }
}
