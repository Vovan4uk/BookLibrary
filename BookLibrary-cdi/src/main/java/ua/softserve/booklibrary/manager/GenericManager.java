package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.Entity;
import ua.softserve.booklibrary.exception.AlreadyExistException;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface GenericManager<T extends Entity> {
    T save(T entity) throws AlreadyExistException;

    T update(T entity);

    void removeByPk(Long id);

    void removeAll(List<T> entities);

    T findByPk(Long id);

    List<T> findAll();

}
