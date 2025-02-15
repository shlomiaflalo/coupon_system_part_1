package facade;

import dao.CategoryDAO;
import dao.CompanyDAO;
import dao.CouponDAO;
import dao.CustomerDAO;
import dao.dbdao.CategoryDBDAO;
import dao.dbdao.CompanyDBDAO;
import dao.dbdao.CouponDBDAO;
import dao.dbdao.CustomerDBDAO;
import exception.CouponSystemException;

import java.sql.SQLException;

/**
 * ClientFacade is an abstract class that serves as a base for different
 * types of client facades in the Coupon Management System.
 * It provides common functionalities and properties required for client operations.
 *
 * This class is a part of the Facade design pattern, and it is extended
 * by concrete facade implementations to interact with the underlying
 * business logic and data access objects.
 *
 * The ClientFacade holds instances of DAOs to perform operations
 * on Company, Coupon, Customer, and Category tables.
 */
public abstract class ClientFacade {
    protected CompanyDAO companyDAO = CompanyDBDAO.getInstance();
    protected CouponDAO couponDAO = CouponDBDAO.getInstance();
    protected CustomerDAO customerDAO = CustomerDBDAO.getInstance();
    protected CategoryDAO categoryDAO = CategoryDBDAO.getInstance();

    public abstract boolean login(String email, String password) throws CouponSystemException, SQLException;


}
