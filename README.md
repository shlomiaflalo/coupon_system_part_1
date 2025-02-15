# Coupon System

## Overview
This is a Java-based Coupon Management System that allows businesses to create and manage coupons while enabling customers to use them. The system is built with a structured architecture, utilizing a DAO pattern for data access and a security layer for authentication and authorization.

## Features
- **User Authentication**: Secure login system for different user types.
- **Coupon Management**: CRUD operations for coupons.
- **Company & Customer Management**: Admin capabilities to manage users.
- **Database Integration**: DAO-based architecture with DB-backed storage.

## Technologies Used
- **Java** (JDK 11 or higher recommended)
- **Maven** (for dependency management)
- **JDBC/MySQL** (for database connectivity)
- **DAO Pattern** (for structured data access)

## Installation & Setup
### Prerequisites
1. Install Java (JDK 11 or later).
2. Install Maven.
3. Set up a MySQL database and configure connection settings.

### Steps to Run
1. Clone or download the project.
2. Navigate to the project root and run:
   ```sh
   mvn clean install
   ```
3. Run the application using:
   ```sh
   java -jar target/coupon_system-1.0-SNAPSHOT.jar
   ```

## Project Structure
```
coupon_system/
│── src/main/java/
│   ├── Program.java             # Main entry point
│   ├── security/
│   │   ├── LoginManager.java    # Handles authentication
│   │   ├── ClientType.java      # Enum for different client types
│   ├── dao/
│   │   ├── CouponDAO.java       # Interface for coupon operations
│   │   ├── CompanyDAO.java      # Interface for company operations
│   ├── dao/dbdao/
│   │   ├── CouponDBDAO.java     # Database implementation of CouponDAO
│   │   ├── CompanyDBDAO.java    # Database implementation of CompanyDAO
```

## Usage
1. **Login as Admin**: Manage companies, customers, and coupons.
2. **Login as Company**: Create and modify coupons.
3. **Login as Customer**: Purchase and use coupons.

## Contribution
Feel free to fork and contribute via pull requests.

## License
This project is open-source and available under the MIT License.

