package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.Entity;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface GenericManager<T extends Entity> {
    void save(T entity);

    T update(T entity);

    void removeByPk(Long id);

    void removeAll(List<T> entities);

    T findByPk(Long id);

    List<T> findAll();

}
