package ua.softserve.booklibrary.dao.home;

public interface GenericHome<T> {
    void save(T entity);

    T update(T entity);

    void removeByPk(Long id);
}
