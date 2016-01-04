package ua.softserve.booklibrary.dao.home;

import ua.softserve.booklibrary.entity.LibraryEntity;

import java.util.Collection;

/**
 * Generic interface for all entity home interfaces.
 * Home interface provide write operations for entity.
 *
 * @param <T> - entity class
 */

public interface GenericHome<T extends LibraryEntity> {

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
	void removeAll(Collection<T> entities);
}