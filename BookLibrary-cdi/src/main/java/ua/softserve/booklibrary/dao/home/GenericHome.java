package ua.softserve.booklibrary.dao.home;

import ua.softserve.booklibrary.entity.Entity;
import ua.softserve.booklibrary.exception.AlreadyExistException;

public interface GenericHome<T extends Entity> {
    T save(T entity) throws AlreadyExistException;

    T update(T entity);

    void removeByPk(Long id);
}
