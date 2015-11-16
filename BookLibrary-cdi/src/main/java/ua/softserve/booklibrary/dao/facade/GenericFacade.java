package ua.softserve.booklibrary.dao.facade;

import java.util.List;

public interface GenericFacade<T> {
    T findByPk(Long id);

    List<T> findAll();
}
