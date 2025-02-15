package facade;

import beans.Company;
import beans.Customer;
import exception.CouponSystemException;
import exception.ErrorMessage;

import java.sql.SQLException;
import java.util.List;

/**
 * AdminFacadeImplementation is a singleton class that extends ClientFacade and implements the AdminFacade interface.
 * It provides administrative functionalities such as managing companies and customers in the Coupon Management System.
 * This class handles operations like adding, updating, deleting, and retrieving companies and customers information.
 */
public class AdminFacadeImplementation extends ClientFacade implements AdminFacade {

    private static final AdminFacadeImplementation instance = new AdminFacadeImplementation();

    public static AdminFacadeImplementation getInstance() {
        return instance;
    }

    private AdminFacadeImplementation() {
    }

    private final String email = "admin@admin.com";
    private final String password = "admin";

    @Override
    public boolean login(String email, String password) throws CouponSystemException {
        if(!(email.equals(this.email) && password.equals(this.password))) {
            throw new CouponSystemException(ErrorMessage.EMAIL_AND_PASSWORD_IS_NOT_CORRECT);
        }
        return true;
    }

    @Override
    public void addCompany(Company company) throws SQLException, CouponSystemException {
        if (companyDAO.isCompanyExistsByName(company.getName())) {
            throw new CouponSystemException(ErrorMessage.COMPANY_NAME_ALREADY_EXISTS);
        }
        if (companyDAO.isCompanyExistsByEmail(company.getEmail())) {
            throw new CouponSystemException(ErrorMessage.COMPANY_EMAIL_ALREADY_EXISTS);
        }
        if (companyDAO.isExist(company.getId())) {
            throw new CouponSystemException(ErrorMessage.COMPANY_ID_ALREADY_EXISTS);
        }

        companyDAO.add(company);
        System.out.println(companyDAO.getLastRecordCompany());
    }

    @Override
    public void updateCompany(Company company) throws CouponSystemException, SQLException {

        if(!companyDAO.isExist(company.getId())){
            throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);
        }

        if(!companyDAO.isCompanyExistsByNameAndId(company.getName(), company.getId())) {
            throw new CouponSystemException(ErrorMessage.CANNOT_EDIT_COMPANY_NAME_AND_ID);
        }


        if(companyDAO.isCompanyEmailExistsExclude(company.getEmail(),company.getId())) {
            throw new CouponSystemException(ErrorMessage.EMAIL_IN_USE_BY_ANOTHER_CUSTOMER);
        }

        companyDAO.update(company.getId(), company);
        System.out.println("Updated company: "+companyDAO.getSingle(company.getId()));
    }

    @Override
    public void deleteCompany(int companyId) throws SQLException, CouponSystemException {
        if(!companyDAO.isExist(companyId)){
            throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);
        }
        couponDAO.deleteCouponPurchaseByCompanyId(companyId);
        couponDAO.deleteCouponsByCompanyId(companyId);
        companyDAO.delete(companyId);
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException {
        return companyDAO.getAll();
    }

    @Override
    public Company getOneCompany(int companyId) throws SQLException, CouponSystemException {
        if(!companyDAO.isExist(companyId)) {
            throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);
        }
        Company company = companyDAO.getSingle(companyId);
        System.out.println(company);
        return company;
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException, CouponSystemException {
        if(customerDAO.isExist(customer.getId())) {
            throw new CouponSystemException(ErrorMessage.CUSTOMER_IS_ALREADY_EXISTS);
        }
        if(customerDAO.isCustomerEmailExists(customer.getEmail())) {
            throw new CouponSystemException(ErrorMessage.EMAIL_IN_USE_BY_ANOTHER_CUSTOMER);
        }
        customerDAO.add(customer);
        System.out.println("Customer been Added: "+ customerDAO.getLastRecordCustomer());
    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException, CouponSystemException {
        if(!customerDAO.isExist(customer.getId())) {
            throw new CouponSystemException(ErrorMessage.CUSTOMER_IS_NOT_EXISTS);
        }

        if(customerDAO.isCustomerEmailExistsExclude(customer.getEmail(),customer.getId())) {
            throw new CouponSystemException(ErrorMessage.EMAIL_IN_USE_BY_ANOTHER_CUSTOMER);
        }

        customerDAO.update(customer.getId(), customer);
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException, CouponSystemException {
        if(!customerDAO.isExist(customerId)) {
            throw new CouponSystemException(ErrorMessage.CUSTOMER_IS_NOT_EXISTS);
        }
        couponDAO.deleteCouponPurchaseByCustomerId(customerId);
        customerDAO.delete(customerId);
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAll();
    }

    @Override
    public Customer getOneCustomer(int customerId) throws SQLException, CouponSystemException {
        if(!customerDAO.isExist(customerId)) {
            throw new CouponSystemException(ErrorMessage.CUSTOMER_IS_NOT_EXISTS);
        }
        Customer customer = customerDAO.getSingle(customerId);
        System.out.println(customer);
        return customer;
    }

    @Override
    public Company getSingleCompanyByEmail(String email) throws SQLException, CouponSystemException {
        if(!companyDAO.isCompanyExistsByEmail(email)) {
            throw new CouponSystemException(ErrorMessage.EMAIL_IS_NOT_FOUND);
        }
        return companyDAO.getSingleByEmail(email);
    }

    @Override
    public Customer getSingleCustomerByEmail(String email) throws SQLException, CouponSystemException {
        if(!customerDAO.isCustomerEmailExists(email)) {
            throw new CouponSystemException(ErrorMessage.EMAIL_IS_NOT_FOUND);
        }
        return customerDAO.getSingleByEmail(email);
    }

    @Override
    public Customer getFirstCustomerRecord() throws SQLException {
        return customerDAO.getFirstRecordCustomer();
    }

    @Override
    public Customer getLastCustomerRecord() throws SQLException {
        return customerDAO.getLastRecordCustomer();
    }

    @Override
    public Company getFirstCompanyRecord() throws SQLException {
        return companyDAO.getFirstRecordCompany();
    }

    @Override
    public Company getLastCompanyRecord() throws SQLException {
        return companyDAO.getLastRecordCompany();
    }



}
