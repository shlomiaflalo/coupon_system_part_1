package facade;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import exception.CouponSystemException;

import java.sql.SQLException;
import java.util.List;

/**
 * The CustomerFacade interface provides methods for customer-related operations in the coupon system.
 */
public interface CustomerFacade {
    void purchase(int customerId, int couponId) throws SQLException, CouponSystemException;
    List<Coupon> getCustomerCoupons(int customerId) throws SQLException, CouponSystemException;
    List<Coupon> getCustomerCoupons(int customerId,Category category) throws SQLException, CouponSystemException;
    List<Coupon> getCustomerCoupons(int customerId,double maxPrice) throws SQLException, CouponSystemException;
    Customer getCustomerDetails(int customerId) throws SQLException, CouponSystemException;
    List<Customer> getAllCustomersWithCoupons() throws SQLException;
    List<Customer> getAllCustomersWithCouponsByCategoryId(int categoryId) throws SQLException, CouponSystemException;
    List<Customer> getAllCustomersWithCouponsByCategory(Category category) throws SQLException, CouponSystemException;
    Customer getCustomerDetailsWithCoupons(int customerId) throws SQLException, CouponSystemException;
    Customer getSingleByEmail(String email) throws SQLException, CouponSystemException;
    Coupon getLastRecordCoupon() throws SQLException;
    Coupon getFirstRecordCoupon() throws SQLException;
    Customer getFirstCustomerRecord() throws SQLException;
    Customer getLastCustomerRecord() throws SQLException;
}
