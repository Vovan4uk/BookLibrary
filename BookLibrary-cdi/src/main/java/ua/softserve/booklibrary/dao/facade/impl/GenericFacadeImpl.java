package ua.softserve.booklibrary.dao.facade.impl;

import ua.softserve.booklibrary.dao.facade.GenericFacade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericFacadeImpl<T> implements GenericFacade<T> {

    @PersistenceContext(unitName = "OracleDS")
    private EntityManager em;

    private final Class<T> entityClass;

    public GenericFacadeImpl() {
        this.entityClass = (Class<T>)
                ((ParameterizedType) getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    @Override
    public T findByPk(Long id) {
        return em.find(entityClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return em.createQuery("select e from " + entityClass.getName() + " e").getResultList();
    }

}
