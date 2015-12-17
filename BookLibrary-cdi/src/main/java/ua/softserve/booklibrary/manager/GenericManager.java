package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.LibraryEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface GenericManager<T extends LibraryEntity> {
    T save(T entity);

    T update(T entity);

    void removeByPk(Long id);

    void removeAll(List<T> entities);

    T findByPk(Long id);

    List<T> findAll();

}
