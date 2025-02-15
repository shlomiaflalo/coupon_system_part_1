package dao;

import beans.Coupon;

import java.sql.SQLException;
import java.util.List;

/**
 * CouponDAO provides methods for performing CRUD operations
 * and other specific operations on Coupon table in the database.
 */
public interface CouponDAO extends TableDAO<Coupon, Integer> {

    void deleteCouponsByCompanyId(Integer companyId) throws SQLException;
    List<Coupon> getCouponsByCompany(Integer companyId) throws SQLException;
    List<Coupon> getCouponsByMaxPriceAndCompanyId(double maxPrice, int companyId) throws SQLException;
    List<Coupon> getCouponsByMaxPriceAndCustomerId(double maxPrice, int customerId) throws SQLException;
    List<Coupon> getCouponsByCategoryIdAndCompanyId(int categoryId, int companyId) throws SQLException;
    void removeAllExpiredCoupons() throws SQLException;
    Coupon getLastRecordCoupon() throws SQLException;
    Coupon getFirstRecordCoupon() throws SQLException;
    List<Coupon> getCustomerCouponsByCategory(int customerId, int categoryId) throws SQLException;
    List<Coupon> getCouponsByCustomer(Integer customerId) throws SQLException;
    boolean isCouponPurchasedByCustomer(int customerId, int couponId) throws SQLException;
    boolean isCouponTypePurchased(int customerId, int couponId) throws SQLException;
    boolean isCouponAvailable(int couponId) throws SQLException;
    boolean dueDateCouponCheck(int couponId) throws SQLException;
    void purchaseCoupon(int customerId, int couponId) throws SQLException;
    void deleteCouponPurchase(int customerId,int couponId) throws SQLException;
    void deleteCouponPurchaseByCouponId(int couponId) throws SQLException;
    void deleteCouponPurchaseByCompanyId(int companyId) throws SQLException;
    void deleteCouponPurchaseByCustomerId(int customerId) throws SQLException;
    void deleteCouponsByCustomerId(int customerId) throws SQLException;
    boolean isCouponTitleExistsByCompanyId(int companyId, String title) throws SQLException;
    boolean isCouponExistsByCouponIdAndCompanyId(int couponId, int companyId) throws SQLException;
    boolean isCouponTitleExistsByCompanyIdExclude(int companyId, Coupon coupon) throws SQLException;

}
