package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.Entity;

import java.util.List;

public interface GenericFacade<T extends Entity> {
    T findByPk(Long id);
    List<T> findAll();
}
