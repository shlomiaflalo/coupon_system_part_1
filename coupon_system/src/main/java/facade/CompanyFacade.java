package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exception.CouponSystemException;

import java.sql.SQLException;
import java.util.List;

/**
 * The CompanyFacade interface provides methods to manage company-related operations
 * such as adding, updating, and deleting coupons, retrieving company and coupon details,
 * and obtaining all companies with their associated coupons.
 */
public interface CompanyFacade {
    void addCoupon(int companyId, Coupon coupon) throws SQLException, CouponSystemException;
    void updateCoupon(int companyId, Coupon coupon) throws CouponSystemException, SQLException;
    void deleteCoupon(int companyId, int couponId) throws CouponSystemException, SQLException;
    List<Coupon> getCompanyCoupons(int companyId) throws SQLException, CouponSystemException;
    List<Company> getAllCompaniesWithCoupons() throws SQLException;
    List<Company> getAllCompaniesWithCouponsByCategoryId(int categoryId) throws SQLException;
    List<Company> getAllCompaniesWithCouponsByCategory(Category category) throws SQLException;
    List<Coupon> getCompanyCoupons(int companyId, Category category) throws SQLException, CouponSystemException;
    List<Coupon> getCompanyCoupons(int companyId, double maxPrice) throws SQLException, CouponSystemException;
    Company getCompanyDetails(int companyId) throws CouponSystemException, SQLException;
    Company getCompanyDetailsWithCoupons(int companyId) throws SQLException;
    Company getSingleByEmail(String email) throws SQLException, CouponSystemException;

    Company getFirstCompanyRecord() throws SQLException;

    Company getLastCompanyRecord() throws SQLException;
}
