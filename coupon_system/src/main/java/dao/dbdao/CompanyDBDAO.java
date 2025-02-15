package dao.dbdao;

import beans.Category;
import beans.Company;
import beans.Coupon;
import dao.CompanyDAO;
import db.ConvertUtils;
import db.DataBaseUtils;

import java.sql.*;
import java.util.*;

//final because no one can't extend it and @Override it

/**
 * The CompanyDBDAO class provides an implementation of the CompanyDAO interface,
 * enabling interactions with the company-related data within the database.
 * This class encompasses several methods for performing CRUD operations and
 * querying company data in the database.
 */
public final class CompanyDBDAO implements CompanyDAO {

    private final static CompanyDBDAO instance = new CompanyDBDAO();
    private static final Map<Integer, Object> params = new HashMap<>();

    private CompanyDBDAO() {
    }

    public static CompanyDBDAO getInstance() {
        return instance;
    }


    @Override
    public void add(Company company) throws SQLException {
        final String query
                = "INSERT INTO coupon_system.companies VALUES (0, ?, ?, ?);";
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void update(Integer companyId, Company company) throws SQLException {
        final String query = "UPDATE coupon_system.companies SET name = ?, email = ?, password = ? WHERE id = ?";
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        params.put(4, companyId);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }

    @Override
    public void delete(Integer companyId) throws SQLException {
        final String query = "DELETE FROM coupon_system.companies WHERE id = ?";
        params.put(1, companyId);
        DataBaseUtils.runQuery(query, params);
        params.clear();
    }


    @Override
    public Company getFirstRecordCompany() throws SQLException {
        final String query = "SELECT * from coupon_system.companies order by id LIMIT 1";
        List<?> list = DataBaseUtils.runQueryWithResult(query);
        if (!list.isEmpty()) {
            return ConvertUtils.
                    objectToCompany((Map<String, Object>) list.get(0));
        }
        return null;
    }

    @Override
    public Company getLastRecordCompany() throws SQLException {
        final String query = "SELECT * from coupon_system.companies order by id DESC LIMIT 1";
        List<?> list = DataBaseUtils.runQueryWithResult(query);
        if (!list.isEmpty()) {
            return ConvertUtils.
                    objectToCompany((Map<String, Object>) list.get(0));
        }
        return null;
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyId) throws SQLException {
        final String companiesCoupons = "SELECT * FROM coupon_system.coupons WHERE company_id = ?";
        params.put(1, companyId);
        List<?> couponList = DataBaseUtils.runQueryWithResult(companiesCoupons, params);
        params.clear();

        List<Coupon> coupons = new ArrayList<>();

        for (Object obj : couponList) {
            Coupon coupon = ConvertUtils.
                    objectToCoupon((Map<String, Object>) obj);
            coupons.add(coupon);
        }
        return coupons;
    }

    @Override
    public List<Company> getAllCompaniesWithCoupons() throws SQLException {
        List<Company> companiesOriginal = getAll();
        List<Company> companiesWithCoupons = new ArrayList<>();

        if (!companiesOriginal.isEmpty()) {
            for (Company company : companiesOriginal) {
                List<Coupon> companyCoupons = CouponDBDAO.getInstance().
                        getCouponsByCompany(company.getId());
                if (!companyCoupons.isEmpty()) {
                    company.setCoupons(companyCoupons);
                    companiesWithCoupons.add(company);
                }
            }
        }
        return companiesWithCoupons;
    }

    @Override
    public List<Company> getAllCompaniesWithCouponsByCategoryId(int categoryId) throws SQLException {
        List<Company> companiesWithCoupons = getAllCompaniesWithCoupons();
        List<Company> companiesWithCouponsByCategory = new ArrayList<>();
        List<Coupon> couponsByCategory = new ArrayList<>();

        if (!companiesWithCoupons.isEmpty()) {
            for (Company companiesWithCoupon : companiesWithCoupons) {
                for (int x = 0; x < companiesWithCoupon.getCoupons().size(); x++) {
                    Coupon coupon = companiesWithCoupon.getCoupons().get(x);
                    if (coupon.getCategory().getCategoryId() == categoryId) {
                        couponsByCategory.add(coupon);
                    }
                }
                if (!couponsByCategory.isEmpty()) {
                    companiesWithCoupon.setCoupons(couponsByCategory);
                    couponsByCategory.clear();
                    companiesWithCouponsByCategory.add(companiesWithCoupon);
                }
            }
        }
        return companiesWithCouponsByCategory;
    }

    @Override
    public List<Company> getAllCompaniesWithCouponsByCategory(Category category) throws SQLException {
        List<Company> companiesWithCoupons = getAllCompaniesWithCoupons();
        List<Company> companiesWithCouponsByCategory = new ArrayList<>();
        List<Coupon> couponsByCategory = new ArrayList<>();

        if (!companiesWithCoupons.isEmpty()) {
            for (Company companiesWithCoupon : companiesWithCoupons) {
                for (int x = 0; x < companiesWithCoupon.getCoupons().size(); x++) {
                    Coupon coupon = companiesWithCoupon.getCoupons().get(x);
                    if (coupon.getCategory() == category) {
                        couponsByCategory.add(coupon);
                    }
                }
                if (!couponsByCategory.isEmpty()) {
                    companiesWithCoupon.setCoupons(couponsByCategory);
                    couponsByCategory.clear();
                    companiesWithCouponsByCategory.add(companiesWithCoupon);
                }
            }
        }
        return companiesWithCouponsByCategory;
    }

    @Override
    public List<Company> getAll() throws SQLException {
        final String queryCompanies = "SELECT * FROM coupon_system.companies";
        List<Company> companies = new ArrayList<>();
        List<?> listOfCompanies = DataBaseUtils.runQueryWithResult(queryCompanies);
        for (Object obj : listOfCompanies) {
            Company company = ConvertUtils.
                    objectToCompany((Map<String, Object>) obj);

            companies.add(company);
        }
        return companies;
    }

    @Override
    public Company getSingle(Integer companyId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.companies WHERE id = ?";
        params.put(1, companyId);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        if (!list.isEmpty()) {
            return ConvertUtils.
                    objectToCompany((Map<String, Object>) list.get(0));
        }
        return null;
    }

    @Override
    public Company getSingleByEmail(String email) throws SQLException {
        final String query = "SELECT * FROM coupon_system.companies WHERE email = ?";
        params.put(1, email);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        if (!list.isEmpty()) {
            return ConvertUtils.
                    objectToCompany((Map<String, Object>) list.get(0));
        }
        return null;
    }

    @Override
    public boolean isExist(Integer companyId) throws SQLException {
        final String query = "SELECT * FROM coupon_system.companies WHERE id = ?";
        params.put(1, companyId);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public boolean isCompanyEmailExistsExclude(String email, int companyId) throws SQLException {
        // Adding wildcards to the email parameter
        final String query = "SELECT * FROM coupon_system.companies WHERE email = ? AND id != ?";

        // Create the parameter map
        params.put(1, email);  // Adding '%' before and after the email
        params.put(2, companyId);

        // Execute the query
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();

        // Return whether a company exists with the given email (excluding the specified company)
        return !list.isEmpty();
    }


    @Override
    public boolean isCompanyExistsByEmailAndPassword(String email, String password) throws SQLException {
        final String query = "SELECT * FROM coupon_system.companies WHERE email = ? AND password = ?";
        params.put(1, email);
        params.put(2, password);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public boolean isCompanyExistsByName(String name) throws SQLException {
        final String query = "SELECT * FROM coupon_system.companies WHERE name = ?";
        params.put(1, name);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public boolean isCompanyExistsByEmail(String email) throws SQLException {
        final String query = "SELECT * FROM coupon_system.companies WHERE email = ?";
        params.put(1, email);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }

    @Override
    public boolean isCompanyExistsByNameAndId(String name, int id) throws SQLException {
        final String query = "SELECT * FROM coupon_system.companies WHERE name = ? AND id = ?";
        params.put(1, name);
        params.put(2, id);
        List<?> list = DataBaseUtils.runQueryWithResult(query, params);
        params.clear();
        return !list.isEmpty();
    }


    @Override
    public Company getCompanyWithCoupons(int companyId) throws SQLException {
        List<Company> CompanyWithCoupons = getAllCompaniesWithCoupons();
        for (Company company : CompanyWithCoupons) {
            if (company.getId() == companyId) {
                return company;
            }
        }
        return null;
    }

}