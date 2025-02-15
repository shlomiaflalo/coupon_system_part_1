package dao.dbdao;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import dao.CustomerDAO;
import db.ConvertUtils;
import db.DataBaseUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CustomerDBDAO is a Data Access Object (DAO) that provides methods to interact with the
 * customers' data in the database. This class uses the singleton pattern to ensure that
 * only one instance of this DAO exists.
 */
public final class CustomerDBDAO implements CustomerDAO {

    private final static CustomerDBDAO instance=new CustomerDBDAO();
    private static final Map<Integer, Object> params = new HashMap<>();


    private CustomerDBDAO() {
    }

    public static CustomerDBDAO getInstance() {
        return instance;
    }

    @Override
    public boolean isCustomerEmailExistsExclude(String email, int customerId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.customers WHERE email = ? AND id != ?";
        params.put(1, email);
        params.put(2, customerId);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public Customer getLastRecordCustomer() throws SQLException {
        final String query = "SELECT * from coupon_system.customers order by id DESC LIMIT 1";
        List<?> list = DataBaseUtils.runQueryWithResult(query);
        if (!list.isEmpty()) {
            return ConvertUtils.
                    objectToCustomer((Map<String, Object>) list.get(0));
        }
        return null;
    }

    @Override
    public Customer getFirstRecordCustomer() throws SQLException {
        final String query = "SELECT * from coupon_system.customers order by id LIMIT 1";
        List<?> list = DataBaseUtils.runQueryWithResult(query);
        if (!list.isEmpty()) {
            return ConvertUtils.
                    objectToCustomer((Map<String, Object>) list.get(0));
        }
        return null;
    }

    @Override
    public Customer getSingleByEmail(String email) throws SQLException {
        final String query = "SELECT * FROM coupon_system.customers WHERE email = ?";
        params.put(1, email);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        if (!list.isEmpty()) {
            return ConvertUtils.
                    objectToCustomer((Map<String, Object>) list.get(0));
        }
        return null;
    }

    @Override
    public boolean isCustomerEmailExistsById(String email, int customerId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.customers as c" +
                " WHERE c.email = ? AND c.id = ?";
        params.put(1, email);
        params.put(2, customerId);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }
    @Override
    public boolean isCustomerEmailExists(String email) throws SQLException {
        final String query = "SELECT * FROM coupon_system.customers as c WHERE c.email = ?";
        params.put(1, email);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public void add(Customer customer) throws SQLException {
        final String query = "INSERT INTO coupon_system.customers VALUES (0, ?, ?, ? ,?);";
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void update(Integer id, Customer customer) throws SQLException {
        final String query = "UPDATE coupon_system.customers SET first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, id);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void delete(Integer customerId) throws SQLException {
        final String query = "DELETE FROM coupon_system.customers WHERE id = ?";
        params.put(1, customerId);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }


    @Override
    public List<Customer> getAllCustomersByCouponCategory(int categoryId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.customers AS c" +
                "    JOIN coupon_system.purchases AS p " +
                "    ON c.id = p.customer_id JOIN coupon_system.coupons as coupon ON p.coupon_id = coupon.id" +
                "    WHERE coupon.category_id = ?";

        List<Customer> customers = new ArrayList<>();
        params.put(1, categoryId);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        for (Object obj : list) {
            Customer customer = ConvertUtils.
                    objectToCustomer((Map<String, Object>) obj);
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        final String query = "SELECT * FROM coupon_system.customers";

        List<Customer> customers = new ArrayList<>();
        List<?> list = DataBaseUtils.runQueryWithResult(query);
        for (Object obj : list) {
            Customer customer = ConvertUtils.
                    objectToCustomer((Map<String, Object>) obj);
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public Customer getSingle(Integer customerId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.customers WHERE id = ?";
        params.put(1, customerId);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        if (!list.isEmpty()) {
            return ConvertUtils.
                    objectToCustomer((Map<String, Object>) list.get(0));
        }
        return null;
    }

    @Override
    public boolean isExist(Integer customerId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.customers WHERE id = ?";
        params.put(1, customerId);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException {
        final String query = "SELECT * FROM coupon_system.customers WHERE email = ? AND password = ?";
        params.put(1, email);
        params.put(2, password);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public List<Customer> getAllCustomersWithCoupons() throws SQLException {
        List<Customer> customersOriginal = getAll();
        List<Customer> customersWithCoupons = new ArrayList<>();

        if(!customersOriginal.isEmpty()) {
            for (int i = 0; i < customersOriginal.size(); i++) {
                List<Coupon> coupons = CouponDBDAO.getInstance().
                        getCouponsByCustomer(customersOriginal.get(i).getId());

                if(!coupons.isEmpty()) {
                    customersOriginal.get(i).setCoupons(coupons);
                    customersWithCoupons.add(customersOriginal.get(i));
                }
            }
        }
        return customersWithCoupons;
    }

    @Override
    public List<Customer> getAllCustomersWithCouponsByCategoryId(int categoryId) throws SQLException {
        List<Customer> customersWithCoupons =  getAllCustomersWithCoupons();
        List<Customer> customersWithCouponsByCategoryId = new ArrayList<>();
        List<Coupon> couponsByCategory = new ArrayList<>();

        if(!customersWithCoupons.isEmpty()) {
                for (int i = 0; i < customersWithCoupons.size(); i++) {
                    for (int x = 0; x < customersWithCoupons.get(i).getCoupons().size(); x++) {
                    Coupon coupon = customersWithCoupons.get(i).getCoupons().get(x);
                    if (coupon.getCategory().getCategoryId() == categoryId) {
                        couponsByCategory.add(coupon);
                    }
                }
                if (!couponsByCategory.isEmpty()) {
                    customersWithCoupons.get(i).setCoupons(couponsByCategory);
                    couponsByCategory.clear();
                    customersWithCouponsByCategoryId.add(customersWithCoupons.get(i));
                }
            }
        }
        return customersWithCouponsByCategoryId;
    }

    @Override
    public List<Customer> getAllCustomersWithCouponsByCategory(Category category) throws SQLException {
        List<Customer> customersWithCoupons =  getAllCustomersWithCoupons();
        List<Customer> customersWithCouponsByCategoryId = new ArrayList<>();
        List<Coupon> couponsByCategory = new ArrayList<>();

        if(!customersWithCoupons.isEmpty()) {
            for (int i = 0; i < customersWithCoupons.size(); i++) {
                for (int x = 0; x < customersWithCoupons.get(i).getCoupons().size(); x++) {
                    Coupon coupon = customersWithCoupons.get(i).getCoupons().get(x);
                    if (coupon.getCategory().getCategoryId() == category.getCategoryId()) {
                        couponsByCategory.add(coupon);
                    }
                }
                if (!couponsByCategory.isEmpty()) {
                    customersWithCoupons.get(i).setCoupons(couponsByCategory);
                    couponsByCategory.clear();
                    customersWithCouponsByCategoryId.add(customersWithCoupons.get(i));
                }
            }
        }
        return customersWithCouponsByCategoryId;
    }

    @Override
    public Customer getCustomerWithCoupons(int customerId) throws SQLException {
        List<Customer> customersWithCoupons = getAllCustomersWithCoupons();
        for (Customer customersWithCoupon : customersWithCoupons) {
            if(customersWithCoupon.getId()==customerId){
                return customersWithCoupon;
            }
        }
        return null;
    }

}
