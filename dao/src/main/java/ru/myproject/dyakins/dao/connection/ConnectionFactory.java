package ru.myproject.dyakins.dao.connection;

import ru.myproject.dyakins.dao.exception.DAOException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionFactory {
    private static final int DEFAULT_POOL_SIZE = 10;
    private static final String PATH_TO_PROPERTIES = "database.properties";
    private static final String NO_CONNECTION = "Connection is not established: ";
    private BlockingQueue<Connection> pool;
    private String url;
    private String user;
    private String password;

    private ConnectionFactory() {
        this.pool = new LinkedBlockingQueue<>(DEFAULT_POOL_SIZE);
        setProperties();
        fillConnectionPool();
    }

    public static ConnectionFactory getInstance() {
        return ConnectionFactoryHolder.instance;
    }

    public Connection getConnection() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new DAOException(NO_CONNECTION + e.getLocalizedMessage());
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                pool.put(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                pool.put(createConnection());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setProperties() {
        try {
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH_TO_PROPERTIES));
            this.url = properties.getProperty("database.url");
            this.user = properties.getProperty("database.user");
            this.password = properties.getProperty("database.password");
        } catch (IOException e) {
            throw new DAOException(e.getLocalizedMessage());
        }
    }

    private void fillConnectionPool() throws DAOException {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.offer(createConnection());
        }
    }

    private Connection createConnection() throws DAOException {
        Connection connection;
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
           // Class.forName("com.postgresql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DAOException(NO_CONNECTION + e.getLocalizedMessage());
        } /*catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
        return connection;
    }

    //todo Э Возможен такой вариант контроля закрытия всех соединения?
    public boolean shutdown() {
        if (getSize() != DEFAULT_POOL_SIZE) {
            return false;
        }
        while (!pool.isEmpty()) {
            try {
                pool.take().close();
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public int getSize() {
        return pool.size();
    }

    private static class ConnectionFactoryHolder {
        private final static ConnectionFactory instance = new ConnectionFactory();
    }
}