package dao;

import beans.Category;
import beans.Company;
import beans.Customer;

import java.sql.SQLException;
import java.util.List;

/**
 * CustomerDAO is an interface that defines methods for performing operations on Customer objects in the database.
 * It extends the TableDAO interface for generic CRUD operations and includes additional methods specific to Customer.
 */
public interface CustomerDAO extends TableDAO<Customer, Integer> {

    boolean isCustomerEmailExistsExclude(String email, int customerId) throws SQLException;

    Customer getLastRecordCustomer() throws SQLException;

    Customer getFirstRecordCustomer() throws SQLException;

    Customer getSingleByEmail(String email) throws SQLException;

    boolean isCustomerEmailExistsById(String email, int customerId) throws SQLException;
    boolean isCustomerEmailExists(String email) throws SQLException;
    List<Customer> getAllCustomersByCouponCategory(int categoryId) throws SQLException;
    boolean isCustomerExists(String email, String password) throws SQLException;
    List<Customer> getAllCustomersWithCoupons() throws SQLException;
    List<Customer> getAllCustomersWithCouponsByCategoryId(int categoryId) throws SQLException;
    List<Customer> getAllCustomersWithCouponsByCategory(Category category) throws SQLException;
    Customer getCustomerWithCoupons(int customerId) throws SQLException;
}
