package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.LibraryEntity;

import java.util.List;

public interface GenericFacade<T extends LibraryEntity> {
    T findByPk(Long id);
    List<T> findAll();
}
