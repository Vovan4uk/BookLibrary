package ua.softserve.booklibrary.dao.facade.impl;

import ua.softserve.booklibrary.dao.facade.GenericFacade;
import ua.softserve.booklibrary.entity.EntityInterface;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class GenericFacadeImpl<T extends EntityInterface> implements GenericFacade<T> {

    @PersistenceContext(unitName = "OracleDS")
    private EntityManager em;

    private final Class<T> entityClass;

    protected GenericFacadeImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
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
