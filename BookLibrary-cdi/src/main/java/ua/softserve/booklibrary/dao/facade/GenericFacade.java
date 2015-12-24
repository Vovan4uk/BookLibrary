package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.LibraryEntity;

import java.util.List;

/**
 * Generic interface for all entity facades interfaces.
 * Facade interface provide read operations for entity.
 *
 * @param <T> - entity class
 */
public interface GenericFacade<T extends LibraryEntity> {

	/**
	 * Find entity in the database by primary key.
	 *
	 * @param id - entity id
	 * @return Entity
	 */
	T findByPk(Long id);

	/**
	 * Find all entities in the database.
	 *
	 * @return List<T> - list of entities
	 */
	List<T> findAll();
}
