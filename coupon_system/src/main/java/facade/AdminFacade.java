package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exception.CouponSystemException;

import java.sql.SQLException;
import java.util.List;

/**
 * AdminFacade is an interface for administrative actions within the Coupon Management System.
 * It allows for various operations to manage companies and customers, as well as to retrieve specific records.
 */
public interface AdminFacade {

    void addCompany(Company company) throws SQLException, CouponSystemException;
    void updateCompany(Company company) throws CouponSystemException, SQLException;
    void deleteCompany(int companyId) throws SQLException, CouponSystemException;
    List<Company> getAllCompanies() throws SQLException;
    Company getOneCompany(int companyId) throws SQLException, CouponSystemException;
    void addCustomer(Customer customer) throws SQLException, CouponSystemException;
    void updateCustomer(Customer customer) throws SQLException, CouponSystemException;
    void deleteCustomer(int customerId) throws SQLException, CouponSystemException;
    List<Customer> getAllCustomers() throws SQLException;
    Customer getOneCustomer(int customerId) throws SQLException, CouponSystemException;
    Company getSingleCompanyByEmail(String email) throws SQLException, CouponSystemException;
    Customer getSingleCustomerByEmail(String email) throws SQLException, CouponSystemException;

    Customer getLastCustomerRecord() throws SQLException;

    Customer getFirstCustomerRecord() throws SQLException;

    Company getFirstCompanyRecord() throws SQLException;

    Company getLastCompanyRecord() throws SQLException;
}
