package dao.dbdao;

import beans.Coupon;
import dao.CouponDAO;
import db.ConvertUtils;
import db.DataBaseUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CouponDBDAO is a Data Access Object (DAO) class responsible for managing
 * the operations related to Coupon records in the database. This class implements
 * all database interaction methods defined in the CouponDAO interface.
 */
public final class CouponDBDAO implements CouponDAO {

    private final static CouponDBDAO instance = new CouponDBDAO();
    private static final Map<Integer, Object> params = new HashMap<>();

    private CouponDBDAO() {
    }

    public static CouponDBDAO getInstance() {
        return instance;
    }


    @Override
    public void add(Coupon coupon) throws SQLException {
        final String query = "INSERT INTO coupon_system.coupons VALUES (0, ?, ?, ? ,?, ?, ?, ? ,? ,?)";
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategory().getCategoryId());
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void update(Integer couponId, Coupon coupon) throws SQLException {
        final String query = "UPDATE coupon_system.coupons SET title = ?,description = ?, " +
                "start_date = ?,end_date = ?, amount = ?, price = ?, image = ?, category_id = ? WHERE id = ?";
        params.put(1, coupon.getTitle());
        params.put(2, coupon.getDescription());
        params.put(3, coupon.getStartDate());
        params.put(4, coupon.getEndDate());
        params.put(5, coupon.getAmount());
        params.put(6, coupon.getPrice());
        params.put(7, coupon.getImage());
        params.put(8, coupon.getCategory().getCategoryId());
        params.put(9, couponId);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void delete(Integer couponId) throws SQLException {
        final String query = "DELETE FROM coupon_system.coupons WHERE id = ?";
        params.put(1, couponId);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void deleteCouponsByCompanyId(Integer companyId) throws SQLException {
        final String query = "DELETE FROM coupon_system.coupons WHERE company_id = ?";
        params.put(1, companyId);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public List<Coupon> getAll() throws SQLException {
        final String query = "SELECT * FROM coupon_system.coupons";
        List<Coupon> coupons = new ArrayList<>();
        List<?> list = DataBaseUtils.runQueryWithResult(query);
        for (Object obj : list) {
            Coupon coupon = ConvertUtils.
                    objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }

    @Override
    public Coupon getSingle(Integer couponId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.coupons WHERE id = ?";
        params.put(1, couponId);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        if (!list.isEmpty()) {
            return ConvertUtils.
                    objectToCoupon((Map<String, Object>) list.get(0));
        }
        return null;
    }

    @Override
    public List<Coupon> getCouponsByCompany(Integer companyId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.coupons WHERE company_id = ?";
        params.put(1, companyId);
        List<Coupon> coupons = new ArrayList<>();
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        for (Object obj : list) {
            Coupon coupon = ConvertUtils.
                    objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCouponsByMaxPriceAndCompanyId(double maxPrice, int companyId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.coupons WHERE price <= ? AND company_id = ?";
        params.put(1, maxPrice);
        params.put(2, companyId);
        List<Coupon> coupons = new ArrayList<>();
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        for (Object obj : list) {
            Coupon coupon = ConvertUtils.
                    objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCouponsByMaxPriceAndCustomerId(double maxPrice, int customerId) throws SQLException {
        final String query = "SELECT c.id, c.company_id, c.category_id, c.title, c.description," +
                " c.start_date, c.end_date, c.amount, c.price, c.image FROM coupon_system.coupons AS c"+
                " JOIN coupon_system.purchases AS p ON c.id = p.coupon_id" +
                " WHERE c.price <= ? AND p.customer_id = ?";
        params.put(1, maxPrice);
        params.put(2, customerId);
        List<Coupon> coupons = new ArrayList<>();
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        for (Object obj : list) {
            Coupon coupon = ConvertUtils.
                    objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }


    @Override
    public List<Coupon> getCouponsByCategoryIdAndCompanyId(int categoryId, int companyId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.coupons WHERE category_id = ? AND company_id = ?";
        params.put(1, categoryId);
        params.put(2, companyId);
        List<Coupon> coupons = new ArrayList<>();
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        for (Object obj : list) {
            Coupon coupon = ConvertUtils.
                    objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }


    @Override
    public void removeAllExpiredCoupons() throws SQLException {
        final String deleteExpiredPurchasedCoupons = "DELETE FROM coupon_system.purchases " +
                "WHERE coupon_id IN (SELECT id FROM coupon_system.coupons WHERE end_date < CURDATE());";

        final String deleteExpiredCoupons = "DELETE FROM coupon_system.coupons WHERE end_date < CURDATE();";

        DataBaseUtils.runQuery(deleteExpiredPurchasedCoupons);
        DataBaseUtils.runQuery(deleteExpiredCoupons);

    }

    @Override
    public Coupon getLastRecordCoupon() throws SQLException {
        final String query = "SELECT * from coupon_system.coupons order by id DESC LIMIT 1";
        List<?> list = DataBaseUtils.runQueryWithResult(query);
        if (!list.isEmpty()) {
            return ConvertUtils.
                    objectToCoupon((Map<String, Object>) list.get(0));
        }
        return null;
    }

    @Override
    public Coupon getFirstRecordCoupon() throws SQLException {
        final String query = "SELECT * from coupon_system.coupons order by id LIMIT 1";
        List<?> list = DataBaseUtils.runQueryWithResult(query);
        if (!list.isEmpty()) {
            return ConvertUtils.
                    objectToCoupon((Map<String, Object>) list.get(0));
        }
        return null;
    }

    @Override
    public List<Coupon> getCustomerCouponsByCategory(int customerId, int categoryId) throws SQLException {
        final String query = "SELECT c.id, c.company_id, c.category_id, c.title, c.description, c.start_date," +
                " c.end_date, c.amount, c.price, c.image FROM coupon_system.coupons AS c" +
                " JOIN coupon_system.purchases AS p" +
                " ON c.id = p.coupon_id WHERE p.customer_id= ? AND c.category_id= ?";
        params.put(1, customerId);
        params.put(2, categoryId);
        List<Coupon> coupons = new ArrayList<>();
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        for (Object obj : list) {
            Coupon coupon = ConvertUtils.
                    objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCouponsByCustomer(Integer customerId) throws SQLException {
        final String query = "SELECT c.id, c.company_id, c.category_id, c.title, c.description," +
                " c.start_date, c.end_date, c.amount, c.price, c.image FROM coupon_system.coupons AS c" +
                " JOIN coupon_system.purchases AS p " +
                " ON c.id = p.coupon_id WHERE p.customer_id=?";
        params.put(1, customerId);
        List<Coupon> coupons = new ArrayList<>();
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        for (Object obj : list) {
            Coupon coupon = ConvertUtils.
                    objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }


    @Override
    public boolean isExist(Integer couponId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.coupons WHERE id = ?";
        params.put(1, couponId);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public boolean isCouponPurchasedByCustomer(int customerId, int couponId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.purchases WHERE customer_id = ? AND coupon_id = ?";
        params.put(1, customerId);
        params.put(2, couponId);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public boolean isCouponTypePurchased(int customerId, int couponId) throws SQLException {
        final String queryPurchaseBefore = "SELECT * FROM coupon_system.coupons as c"+
                " JOIN coupon_system.purchases as p ON c.id = p.coupon_id" +
                " WHERE p.customer_id = ? AND c.category_id = ?";

        Coupon coupon = getSingle(couponId);
        int categoryId = coupon.getCategory().getCategoryId();

        params.put(1, customerId);
        params.put(2, categoryId);
        List<?> list = DataBaseUtils.runQueryWithResult(queryPurchaseBefore, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public boolean isCouponAvailable(int couponId) throws SQLException {
        final String queryForInventoryCheck = "SELECT * FROM coupon_system.coupons AS c WHERE c.id = ? AND c.amount > 0";
        params.put(1, couponId);
        List<?> list = DataBaseUtils.runQueryWithResult(queryForInventoryCheck, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public boolean dueDateCouponCheck(int couponId) throws SQLException {
        final String dueDateCheck = "SELECT * FROM coupon_system.coupons " +
                "WHERE id = ? AND end_date > CURRENT_DATE();";
        params.put(1, couponId);
        List<?> list = DataBaseUtils.runQueryWithResult(dueDateCheck, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public void purchaseCoupon(int customerId, int couponId) throws SQLException {
        final String query = "INSERT INTO coupon_system.purchases (customer_id, coupon_id) VALUES (?,?)";
        params.put(1, customerId);
        params.put(2, couponId);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void deleteCouponPurchase(int customerId, int couponId) throws SQLException {
        final String query = "DELETE FROM coupon_system.purchases WHERE customer_id = ? AND coupon_id = ?";
        params.put(1, customerId);
        params.put(2, couponId);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void deleteCouponPurchaseByCouponId(int couponId) throws SQLException {
        final String query = "DELETE FROM coupon_system.purchases WHERE coupon_id = ?";
        params.put(1, couponId);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void deleteCouponPurchaseByCompanyId(int companyId) throws SQLException {
        final String query = "DELETE FROM coupon_system.purchases WHERE coupon_id IN (SELECT id FROM coupon_system.coupons WHERE company_id = ?)";
        params.put(1, companyId);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void deleteCouponPurchaseByCustomerId(int customerId) throws SQLException {
        final String query = "DELETE FROM coupon_system.purchases WHERE customer_id = ?";
        params.put(1, customerId);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void deleteCouponsByCustomerId(int customerId) throws SQLException {
        final String query = "DELETE c FROM coupon_system.coupons AS c" +
                " JOIN coupon_system.purchases AS p ON c.id=p.coupon_id" +
                " WHERE p.customer_id=?";
        params.put(1, customerId);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }
    @Override
    public boolean isCouponTitleExistsByCompanyId(int companyId, String title) throws SQLException {
        final String query = "SELECT * FROM coupon_system.coupons" +
                " WHERE company_id = ? AND title = ?";
        params.put(1, companyId);
        params.put(2, title);
        DataBaseUtils.runQuery(query, params);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }


    @Override
    public boolean isCouponExistsByCouponIdAndCompanyId(int couponId, int companyId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.coupons WHERE id = ? AND company_id = ?";
        params.put(1, couponId);
        params.put(2, companyId);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public boolean isCouponTitleExistsByCompanyIdExclude(int companyId, Coupon coupon) throws SQLException {
        final String query = "SELECT * FROM coupon_system.coupons WHERE company_id = ? AND title = ? AND" +
                " id != ?";
        params.put(1, companyId);
        params.put(2, coupon.getTitle());
        params.put(3, coupon.getId());
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }
}
