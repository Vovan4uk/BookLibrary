package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.EntityInterface;

import java.util.List;

public interface GenericFacade<T extends EntityInterface> {
    T findByPk(Long id);
    List<T> findAll();
}
