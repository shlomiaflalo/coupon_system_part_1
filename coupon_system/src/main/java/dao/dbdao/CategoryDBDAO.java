package dao.dbdao;

import beans.Category;
import dao.CategoryDAO;
import db.ConvertUtils;
import db.DataBaseUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CategoryDBDAO is a singleton class that provides database access methods
 * for performing CRUD operations on the Category table.
 * It implements the CategoryDAO interface.
 */
public final class CategoryDBDAO implements CategoryDAO {

    private final static CategoryDBDAO instance = new CategoryDBDAO();
    private static final Map<Integer, Object> params = new HashMap<>();

    private CategoryDBDAO() {
    }

    public static CategoryDBDAO getInstance() {
        return instance;
    }

    @Override
    public void add(Category category) throws SQLException {
        final String query = "INSERT INTO coupon_system.categories VALUES (0, ?);";
        params.put(1, category.name());
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void update(Integer id, Category category) throws SQLException {
        final String query = "UPDATE coupon_system.categories SET name = ? WHERE id = ?";
        params.put(1, category.name());
        params.put(2, id);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }


    @Override
    public void delete(Integer integer) throws SQLException {
        final String query = "DELETE FROM coupon_system.categories WHERE id = ?";
        params.put(1, integer);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public List<Category> getAll() throws SQLException {
        final String query = "SELECT * FROM coupon_system.categories;";
        List<Category> categories = new ArrayList<>();
        List<?> list = DataBaseUtils.runQueryWithResult(query);
        for (Object obj : list) {
            Category category = ConvertUtils.
                    objectToCategory((Map<String, Object>) obj);
            categories.add(category);
        }
        return categories;
    }

    @Override
    public Category getSingle(Integer id) throws SQLException {
        final String query = "SELECT * FROM coupon_system.categories WHERE id = ?";
        params.put(1, id);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        if (!list.isEmpty()) {
            return ConvertUtils.
                    objectToCategory((Map<String, Object>) list.get(0));
        }
        return null;
    }

    @Override
    public boolean isExist(Integer integer) throws SQLException {
        final String query = "SELECT * FROM coupon_system.categories WHERE id = ?";
        params.put(1, integer);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }
}


