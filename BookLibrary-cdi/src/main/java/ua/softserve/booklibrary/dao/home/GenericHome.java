package ua.softserve.booklibrary.dao.home;

import ua.softserve.booklibrary.entity.LibraryEntity;
import ua.softserve.booklibrary.exception.AlreadyExistException;

import java.util.Set;

public interface GenericHome<T extends LibraryEntity> {
    T save(T entity) throws AlreadyExistException;

    T update(T entity);

    void removeByPk(Long id);

    void removeAll(Set<T> entities);
}
