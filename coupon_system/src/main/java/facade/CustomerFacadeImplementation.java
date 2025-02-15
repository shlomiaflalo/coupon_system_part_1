package facade;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import exception.CouponSystemException;
import exception.ErrorMessage;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


/**
 * The CustomerFacadeImplementation class provides an implementation of the CustomerFacade interface.
 * This class handles various customer-related operations such as login, purchasing coupons, and retrieving customer details.
 */
public class CustomerFacadeImplementation extends ClientFacade implements CustomerFacade {

    private static final CustomerFacadeImplementation instance = new CustomerFacadeImplementation();

    public static CustomerFacadeImplementation getInstance() {
        return instance;
    }

    private CustomerFacadeImplementation() {
    }

    @Override
    public boolean login(String email, String password) throws SQLException, CouponSystemException {
        if(!customerDAO.isCustomerExists(email, password)) {
            throw new CouponSystemException(ErrorMessage.EMAIL_AND_PASSWORD_IS_NOT_CORRECT);
        }
        return true;
    }

    @Override
    public void purchase(int customerId, int couponId) throws SQLException, CouponSystemException {
        //1. First Check: if coupon exists
        if (!couponDAO.isExist(couponId)) {
            throw new CouponSystemException(ErrorMessage.COUPON_NOT_EXISTS);
        }

        //2. 2ND Check: if customer exists
        if (!customerDAO.isExist(customerId)) {
            throw new CouponSystemException(ErrorMessage.CUSTOMER_IS_NOT_EXISTS);
        }

        Coupon couponFromDb = couponDAO.getSingle(couponId);

        //3. 3rd Check: if coupon have been purchased by customer
        if (couponDAO.isCouponPurchasedByCustomer(customerId, couponId)) {
            throw new CouponSystemException(ErrorMessage.CUSTOMER_BOUGHT_THIS_COUPON_BEFORE);
        }

        //4. Check if customer bought this coupon type before
        if (couponDAO.isCouponTypePurchased(customerId, couponId)) {
            throw new CouponSystemException(ErrorMessage.CUSTOMER_BOUGHT_THIS_COUPON_TYPE_BEFORE);
        }

        //5. Check if the coupon a customer trying to buy still exists on the inventory
        if (couponFromDb.getAmount() <= 0) {
            throw new CouponSystemException(ErrorMessage.COUPON_IS_OUT_OF_STOCK);
        }
        //6. Checking that the due date of a coupon wasn't arrived.
        if (couponFromDb.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new CouponSystemException(ErrorMessage.DUE_DATE_COUPON);
        }

        //7. Purchasing the coupon
        couponDAO.purchaseCoupon(customerId, couponId);
        //8. Lowering the inventory's coupons in 1 (-1 coupon after purchasing it)
        couponFromDb.setAmount(couponFromDb.getAmount() - 1);
        couponDAO.update(couponId, couponFromDb);
        System.out.println("Thank you four purchasing coupon: "+couponFromDb);
    }


    @Override
    public List<Coupon> getCustomerCoupons(int customerId) throws SQLException, CouponSystemException {
        if (!customerDAO.isExist(customerId)) {
            throw new CouponSystemException(ErrorMessage.CUSTOMER_IS_NOT_EXISTS);
        }
        return couponDAO.getCouponsByCustomer(customerId);
    }


    @Override
    public List<Coupon> getCustomerCoupons(int customerId,Category category) throws SQLException, CouponSystemException {
        if (!customerDAO.isExist(customerId)) {
            throw new CouponSystemException(ErrorMessage.CUSTOMER_IS_NOT_EXISTS);
        }
        return couponDAO.getCustomerCouponsByCategory(customerId,category.getCategoryId());
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerId,double maxPrice) throws SQLException, CouponSystemException {
        if (!customerDAO.isExist(customerId)) {
            throw new CouponSystemException(ErrorMessage.CUSTOMER_IS_NOT_EXISTS);
        }
        return couponDAO.getCouponsByMaxPriceAndCustomerId(maxPrice,customerId);
    }

    @Override
    public Customer getCustomerDetails(int customerId) throws SQLException, CouponSystemException {
        if (!customerDAO.isExist(customerId)) {
            throw new CouponSystemException(ErrorMessage.CUSTOMER_IS_NOT_EXISTS);
        }
        return customerDAO.getSingle(customerId);
    }


//    Extra
    @Override
    public List<Customer> getAllCustomersWithCoupons() throws SQLException {
        return customerDAO.getAllCustomersWithCoupons();
    }

    @Override
    public List<Customer> getAllCustomersWithCouponsByCategoryId(int categoryId) throws SQLException, CouponSystemException {
        List<Customer> customers = customerDAO.getAllCustomersWithCouponsByCategoryId(categoryId);
        if(!categoryDAO.isExist(categoryId)) {
            throw new CouponSystemException(ErrorMessage.WRONG_CATEGORY);
        }
        return customers;
    }

    @Override
    public List<Customer> getAllCustomersWithCouponsByCategory(Category category) throws SQLException, CouponSystemException {
        List<Customer> customers = customerDAO.getAllCustomersWithCouponsByCategory(category);
        if(!categoryDAO.isExist(category.getCategoryId())) {
            throw new CouponSystemException(ErrorMessage.WRONG_CATEGORY);
        }
        return customers;
    }

    @Override
    public Customer getCustomerDetailsWithCoupons(int customerId) throws SQLException, CouponSystemException {
        if (!customerDAO.isExist(customerId)) {
            throw new CouponSystemException(ErrorMessage.CUSTOMER_IS_NOT_EXISTS);
        }
        return customerDAO.getCustomerWithCoupons(customerId);
    }

    @Override
    public Customer getSingleByEmail(String email) throws SQLException, CouponSystemException {
        if(!customerDAO.isCustomerEmailExists(email)) {
            throw new CouponSystemException(ErrorMessage.EMAIL_IS_NOT_FOUND);
        }
        return customerDAO.getSingleByEmail(email);
    }

    @Override
    public Coupon getLastRecordCoupon() throws SQLException {
        return couponDAO.getLastRecordCoupon();
    }

    @Override
    public Coupon getFirstRecordCoupon() throws SQLException {
        return couponDAO.getFirstRecordCoupon();
    }

    @Override
    public Customer getFirstCustomerRecord() throws SQLException {
        return customerDAO.getFirstRecordCustomer();
    }

    @Override
    public Customer getLastCustomerRecord() throws SQLException {
        return customerDAO.getLastRecordCustomer();
    }
}
