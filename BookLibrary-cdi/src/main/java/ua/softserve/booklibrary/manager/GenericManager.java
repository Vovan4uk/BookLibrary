package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.LibraryEntity;

import java.util.List;

/**
 * Generic interface for all managers interfaces.
 * Manager interface provide business logic operations
 *
 * @param <T> entity class
 */
public interface GenericManager<T extends LibraryEntity> {

	/**
	 * Persist entity in the database.
	 *
	 * @param entity Entity to save
	 * @return created Entity
	 */
	T save(T entity);

	/**
	 * Update entity in the database.
	 *
	 * @param entity Entity to update
	 * @return updated Entity
	 */
	T update(T entity);

	/**
	 * Delete the entity from the database by primary key.
	 *
	 * @param id Entity id
	 */
	void removeByPk(Long id);

	/**
	 * Removes entities from database
	 *
	 * @param entities Entities collection
	 */
	void removeAll(List<T> entities);

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