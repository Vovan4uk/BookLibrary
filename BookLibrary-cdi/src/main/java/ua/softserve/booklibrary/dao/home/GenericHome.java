package ua.softserve.booklibrary.dao.home;

import ua.softserve.booklibrary.entity.EntityInterface;

public interface GenericHome<T extends EntityInterface> {
    void save(T entity);

    T update(T entity);

    void removeByPk(Long id);
}
