package security;

import exception.CouponSystemException;
import facade.*;

import java.sql.SQLException;

/**
 * LoginManager is a singleton class responsible for managing the login process
 * for different types of clients in the Coupon Management System.
 * It provides a centralized mechanism to authenticate users based on their
 * email, password, and client type (ADMINISTRATOR, COMPANY, CUSTOMER).
 */
public class LoginManager {

    private final static LoginManager instance = new LoginManager();

    private AdminFacadeImplementation adminFacade;
    private CustomerFacadeImplementation customerFacade;
    private CompanyFacadeImplementation companyFacade;

    public static LoginManager getInstance() {
        return instance;
    }

    private LoginManager(){
        adminFacade = AdminFacadeImplementation.getInstance();
        companyFacade = CompanyFacadeImplementation.getInstance();
        customerFacade = CustomerFacadeImplementation.getInstance();
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException, SQLException {
        switch (clientType) {
            case ADMINISTRATOR: {
                if (adminFacade.login(email, password)) {
                    return adminFacade;
                }
            }
            case COMPANY: {
                if (companyFacade.login(email, password)) {
                    return companyFacade;
                }
            }
            case CUSTOMER: {
                if (customerFacade.login(email, password)) {
                    return customerFacade;
                }
            }

        }
        return null;
    }
}
