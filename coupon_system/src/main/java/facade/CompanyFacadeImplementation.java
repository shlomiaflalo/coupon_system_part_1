package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import exception.CouponSystemException;
import exception.ErrorMessage;

import java.sql.SQLException;
import java.util.List;

/**
 * The CompanyFacadeImplementation class provides various operations
 * for managing company's coupons and details. It implements the
 * CompanyFacade interface and extends the ClientFacade abstract class.
 * The class follows a singleton pattern ensuring only one instance
 * of the facade is created.
 */
public class CompanyFacadeImplementation extends ClientFacade implements CompanyFacade {

    private static final CompanyFacadeImplementation instance = new CompanyFacadeImplementation();

    public static CompanyFacadeImplementation getInstance() {
        return instance;
    }

    private CompanyFacadeImplementation() {
    }

    @Override
    public boolean login(String email, String password) throws SQLException, CouponSystemException {
        if(!companyDAO.isCompanyExistsByEmailAndPassword(email, password)) {
            throw new CouponSystemException(ErrorMessage.EMAIL_AND_PASSWORD_IS_NOT_CORRECT);
        }
        return true;
    }

    @Override
    public void addCoupon(int companyId, Coupon coupon) throws SQLException, CouponSystemException {
        if (!companyDAO.isExist(companyId)) {
            throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);
        }
        if (couponDAO.isExist(coupon.getId())) {
            throw new CouponSystemException(ErrorMessage.COUPON_ALREADY_EXISTS);
        }
        if (couponDAO.isCouponTitleExistsByCompanyId(companyId ,coupon.getTitle())) {
            throw new CouponSystemException(ErrorMessage.COUPON_TITLE_EXISTS_FOR_THIS_COMPANY);
        }
        couponDAO.add(coupon);
    }

    @Override
    public void updateCoupon(int companyId, Coupon coupon) throws CouponSystemException, SQLException {
        if (!companyDAO.isExist(companyId)) {
            throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);
        }

        if (!couponDAO.isExist(coupon.getId())) {
            throw new CouponSystemException(ErrorMessage.COUPON_NOT_EXISTS);
        }

        if(!couponDAO.isCouponExistsByCouponIdAndCompanyId(coupon.getId(), companyId)) {
            throw new CouponSystemException(ErrorMessage.CANNOT_EDIT_COUPON_OF_ANOTHER_COMPANY);
        }

        //In case the client want to update coupon with title that exists for this company
        //According to the instructions we cannot add a new coupon with duplicate title
        if (couponDAO.isCouponTitleExistsByCompanyIdExclude(companyId ,coupon)) {
            throw new CouponSystemException(ErrorMessage.COUPON_TITLE_EXISTS_FOR_THIS_COMPANY);
        }
        couponDAO.update(coupon.getId(),coupon);
    }

    @Override
    public void deleteCoupon(int companyId, int couponId) throws CouponSystemException, SQLException {
        if(!companyDAO.isExist(companyId)){
            throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);
        }
        if(!couponDAO.isExist(couponId)){
            throw new CouponSystemException(ErrorMessage.COUPON_NOT_EXISTS);
        }

        couponDAO.deleteCouponPurchaseByCouponId(couponId);
        couponDAO.delete(couponId);
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyId) throws SQLException, CouponSystemException {
        if(!companyDAO.isExist(companyId)){
            throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);
        }
        return companyDAO.getCompanyCoupons(companyId);
    }

    @Override
    public List<Company> getAllCompaniesWithCoupons() throws SQLException {
        return companyDAO.getAllCompaniesWithCoupons();
    }

    @Override
    public List<Company> getAllCompaniesWithCouponsByCategoryId(int categoryId) throws SQLException {
        return companyDAO.getAllCompaniesWithCouponsByCategoryId(categoryId);
    }

    @Override
    public List<Company> getAllCompaniesWithCouponsByCategory(Category category) throws SQLException {
        return companyDAO.getAllCompaniesWithCouponsByCategory(category);
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyId, Category category) throws SQLException, CouponSystemException {
        if(!companyDAO.isExist(companyId)) {
            throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);
        }
        return couponDAO.getCouponsByCategoryIdAndCompanyId(category.getCategoryId(),companyId);
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyId, double maxPrice) throws SQLException, CouponSystemException {
        if(!companyDAO.isExist(companyId)) {
            throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);
        }
        if(maxPrice<=0 || maxPrice>1_000_000){
            throw new CouponSystemException(ErrorMessage.COUPON_MAX_PRICE_IS_OUT_OF_RANGE);
        }
        return couponDAO.getCouponsByMaxPriceAndCompanyId(maxPrice,companyId);
    }

    @Override
    public Company getCompanyDetails(int companyId) throws CouponSystemException, SQLException {
        if(!companyDAO.isExist(companyId)) {
            throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);
        }
        return companyDAO.getSingle(companyId);
    }

    @Override
    public Company getCompanyDetailsWithCoupons(int companyId) throws SQLException {
        Company company = companyDAO.getCompanyWithCoupons(companyId);
        if(company==null){
            System.out.println("No coupons for this company");
            return null;
        }
        return company;
    }

    @Override
    public Company getSingleByEmail(String email) throws SQLException, CouponSystemException {
        if(!companyDAO.isCompanyExistsByEmail(email)) {
            throw new CouponSystemException(ErrorMessage.EMAIL_IS_NOT_FOUND);
        }
        return companyDAO.getSingleByEmail(email);
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
