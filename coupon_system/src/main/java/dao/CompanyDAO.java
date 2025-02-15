package dao;

import beans.Category;
import beans.Company;
import beans.Coupon;

import java.sql.SQLException;
import java.util.List;

/**
 * CompanyDAO is an interface for performing CRUD operations and
 * other complex queries related to the Company table in the database.
 */
public interface CompanyDAO extends TableDAO<Company, Integer> {
    Company getSingleByEmail(String email) throws SQLException;

    boolean isCompanyEmailExistsExclude(String email, int companyId) throws SQLException;
    boolean isCompanyExistsByEmailAndPassword(String email, String password) throws SQLException;
    boolean isCompanyExistsByName(String name) throws SQLException;
    boolean isCompanyExistsByEmail(String email) throws SQLException;
    boolean isCompanyExistsByNameAndId(String name, int id) throws SQLException;

    Company getFirstRecordCompany() throws SQLException;

    Company getLastRecordCompany() throws SQLException;

    List<Coupon> getCompanyCoupons(int companyId) throws SQLException;
    List<Company> getAllCompaniesWithCoupons() throws SQLException;
    List<Company> getAllCompaniesWithCouponsByCategoryId(int categoryId) throws SQLException;
    List<Company> getAllCompaniesWithCouponsByCategory(Category category) throws SQLException;

    Company getCompanyWithCoupons(int companyId) throws SQLException;
}
