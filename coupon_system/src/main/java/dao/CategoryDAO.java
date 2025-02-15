package dao;

import beans.Category;

/**
 * CategoryDAO provides an interface for CRUD operations on Category table.
 * It extends TableDAO to inherit basic database operation methods.
 *
 * It includes operations to:
 * - Add a new Category
 * - Update an existing Category
 * - Delete a Category by its ID
 * - Retrieve all categories
 * - Retrieve a single Category by its ID
 * - Check if a Category exists by its ID
 */
public interface CategoryDAO extends TableDAO<Category, Integer> {
}
