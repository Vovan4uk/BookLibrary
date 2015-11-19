package ua.softserve.booklibrary.dao.home;

import ua.softserve.booklibrary.entity.Entity;

public interface GenericHome<T extends Entity> {
    void save(T entity);

    T update(T entity);

    void removeByPk(Long id);
}
