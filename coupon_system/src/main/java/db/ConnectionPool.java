package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * Manages a pool of database connections using JDBC.
 * This class follows the Singleton design pattern to ensure a single instance
 * across the application. Connections are managed using a stack to facilitate
 * reuse of database connections, improving performance and resource management.
 */
public final class ConnectionPool {

    /*final class cannot being use as  *parent class* - we can't extend final classes
    (Therefore we don't want any other class to extends ConnectionPool)*/

    private final static ConnectionPool instance = new ConnectionPool();
    private final Stack<Connection> connectionPool=new Stack<>();
    private final String URL="jdbc:mysql://localhost:3306/coupon_system?createDatabaseIfNotExist=true";
    private final String USER="root";
    private final String PASSWORD="shlomi123";
    private final int poolSize=10;



    private ConnectionPool() {
        initializePool();
    }

    public void initializePool(){
        try {
            for (int i = 0; i < poolSize; i++) {
                connectionPool.push(DriverManager.getConnection
                        (URL, USER, PASSWORD));
            }
        }catch (SQLException s){
            System.out.println("Error while initializing connection pool");
        }
    }


    public static ConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection(){
        synchronized (connectionPool) {
            if (connectionPool.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("connection error with" +
                            " getting connection");
                }
            }
        }
        return connectionPool.pop();
    }

    public synchronized void releaseConnection(Connection connection){
        synchronized (connectionPool) {
            connectionPool.push(connection);
            notify();
        }
    }

    public void closeAllConnections() {
        synchronized (connectionPool) {
            //Here we're waiting for all connections to be back into the pool and only
            //then we close them because maybe they in a middle of a task
            while (connectionPool.size() != poolSize) {
                try {
                    /*Here we wait for notify() of releaseConnection function, once connection
                    is pushed back and notify ->  connectionPool.wait() stop waiting ->
                    and check the loop again ->  once all
                    connections are back  -> while loop is over its condition
                    (better than sleep of a thread class)*/
                    connectionPool.wait();
                } catch (InterruptedException e) {
                    System.out.println("Thread error");
                }
            }
        }
        for (int i = 0; i < connectionPool.size(); i++) {
            try {
                connectionPool.get(i).close();
            } catch (SQLException e) {
                System.out.println("Sql error");
            }
        }
    }
}
