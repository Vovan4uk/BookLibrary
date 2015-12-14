package ua.softserve.booklibrary.dao.home;

import ua.softserve.booklibrary.entity.LibraryEntity;

import java.util.Collection;

public interface GenericHome<T extends LibraryEntity> {
    T save(T entity);

    T update(T entity);

    void removeByPk(Long id);

    void removeAll(Collection<T> entities);
}
