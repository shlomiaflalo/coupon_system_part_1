package db;

import java.sql.*;

/**
 * The CreateDataBase class is responsible for setting up the database schema for a coupon system.
 *
 * It contains SQL statements for creating and dropping the schema, as well as creating various tables:
 * companies, customers, categories, coupons, and purchases.
 *
 * The class also provides a method to drop and recreate these structures in the database.
 */
public class CreateDataBase {

    public static final String CREATE_SCHEMA = "create schema `coupon_system`";

    public static final String DROP_SCHEMA = "drop schema `coupon_system`";

    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `coupon_system`.`companies` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";

    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `coupon_system`.`customers` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `first_name` VARCHAR(45) NOT NULL,\n" +
            "  `last_name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE `coupon_system`.`categories` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));";

    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE `coupon_system`.`coupons` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `company_id` INT NOT NULL,\n" +
            "  `category_id` INT NOT NULL,\n" +
            "  `title` VARCHAR(256) NOT NULL,\n" +
            "  `description` VARCHAR(256) NOT NULL,\n" +
            "  `start_date` DATE NOT NULL,\n" +
            "  `end_date` DATE NOT NULL,\n" +
            "  `amount` INT NOT NULL,\n" +
            "  `price` DOUBLE NOT NULL,\n" +
            "  `image` VARCHAR(1000) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\n" +
            "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `company_id`\n" +
            "    FOREIGN KEY (`company_id`)\n" +
            "    REFERENCES `coupon_system`.`companies` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `category_id`\n" +
            "    FOREIGN KEY (`category_id`)\n" +
            "    REFERENCES `coupon_system`.`categories` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";

    private static final String CREATE_TABLE_PURCHASES = "CREATE TABLE `coupon_system`.`purchases` (\n"
            +
            "  `customer_id` INT NOT NULL,\n" +
            "  `coupon_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`customer_id`, `coupon_id`),\n" +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `customer_id`\n" +
            "    FOREIGN KEY (`customer_id`)\n" +
            "    REFERENCES `coupon_system`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `coupon_id`\n" +
            "    FOREIGN KEY (`coupon_id`)\n" +
            "    REFERENCES `coupon_system`.`coupons` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

    public static void dropAndCreateStrategy() throws SQLException {
        DataBaseUtils.runQuery(DROP_SCHEMA);
        DataBaseUtils.runQuery(CREATE_SCHEMA);
        DataBaseUtils.runQuery(CREATE_TABLE_CATEGORIES);
        DataBaseUtils.runQuery(CREATE_TABLE_COMPANIES);
        DataBaseUtils.runQuery(CREATE_TABLE_COUPONS);
        DataBaseUtils.runQuery(CREATE_TABLE_CUSTOMERS);
        DataBaseUtils.runQuery(CREATE_TABLE_PURCHASES);
    }

}
