package ru.myproject.dyakins.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.myproject.dyakins.dao.connection.ConnectionFactory;
import ru.myproject.dyakins.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class FriendsDAOImplTest {
    private Statement statement;
    private FriendsDAO friendsDAO = new FriendsDAOImpl();

    @BeforeClass
    public static void createDataBase() {
        Connection con = ConnectionFactory.getInstance().getConnection();
        try {
            Statement st = con.createStatement();
            st.executeUpdate("CREATE TABLE accounts (" +
                    "  id INT PRIMARY KEY AUTO_INCREMENT," +
                    "  firstName VARCHAR(45) NOT NULL," +
                    "  secondName VARCHAR(45) NOT NULL," +
                    "  middleName VARCHAR(45) NULL," +
                    "  gender CHAR(7) NULL," +
                    "  birthDate DATE NOT NULL," +
                    "  homeAddress VARCHAR(45) NULL," +
                    "  workAddress VARCHAR(45) NULL," +
                    "  email VARCHAR(45) NOT NULL UNIQUE," +
                    "  icq INT NULL," +
                    "  skype VARCHAR(45) NULL," +
                    "  additionalInfo VARCHAR(45) NULL);");

            st.executeUpdate("CREATE TABLE friends (" +
                    "  account_one_id INT NOT NULL," +
                    "  account_two_id INT NOT NULL," +
                    "  status TINYINT(3) NOT NULL DEFAULT '0'," +
                    "  action_account_id INT NOT NULL," +
                    "  CONSTRAINT account_one_id" +
                    "    FOREIGN KEY (account_one_id)" +
                    "    REFERENCES accounts (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT account_two_i" +
                    "    FOREIGN KEY (account_two_id)" +
                    "    REFERENCES accounts (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT action_account_id" +
                    "    FOREIGN KEY (action_account_id)" +
                    "    REFERENCES accounts (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE NO ACTION)");

            st.executeUpdate("CREATE UNIQUE INDEX unique_accounts_id\n" +
                    "  ON friends (account_one_id, account_two_id);");
            if (!st.isClosed()) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.getInstance().releaseConnection(con);
        }
    }

    @Before
    public void init() {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try {
            if (connection != null) {
                statement = connection.createStatement();
            }
            statement.addBatch("INSERT INTO accounts VALUES " +
                    "(NULL, 'Petya', 'Petrov','Ivanovich','male','1989-10-27', NULL, 'Rostov', 'PIP@yandex.ru', " +
                    "NULL, NULL, NULL);");
            statement.addBatch("INSERT INTO accounts VALUES " +
                    "(NULL, 'Alla', 'Petrovna', 'Sergeevna', 'Female', '1999-08-07', 'Moscow', 'Moscow', " +
                    "'Alla1999@yandex.ru', NULL, NULL, NULL);");
            statement.addBatch("INSERT INTO accounts VALUES " +
                    "(NULL, 'Ivan', 'Ivanov',NULL, 'male', '2000-08-27', 'Moscow', 'Moscow', " +
                    "'Ivanov2000@yandex.ru', NULL, NULL, NULL);");
            statement.executeBatch();

            statement.addBatch("INSERT INTO friends VALUES (1,2,1,1)");
            statement.addBatch("INSERT INTO friends VALUES (1,3,1,1)");
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.getInstance().releaseConnection(connection);
        }
    }

    @After
    public void close() {
        try {
            statement.addBatch("DELETE FROM accounts");
            statement.addBatch("ALTER TABLE accounts ALTER COLUMN id RESTART WITH 1");
            statement.executeBatch();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAll() {
        List<Integer> friends = friendsDAO.getAll(1);
        assertEquals(2, friends.size());
        assertTrue(friends.contains(2));
        assertTrue(friends.contains(3));
    }

    @Test
    public void delete() {
        friendsDAO.delete(1, 3);
        List<Integer> friends = friendsDAO.getAll(1);
        assertEquals(1, friends.size());
        assertFalse(friends.contains(3));
    }

    @Test
    public void save() {
        friendsDAO.save(2, 3);
        List<Integer> friends = friendsDAO.getAll(2);
        assertEquals(2, friends.size());
        assertTrue(friends.contains(1));
        assertTrue(friends.contains(3));
    }

    @Test(expected = DAOException.class)
    public void saveNotUniqueAccountPair() {
        friendsDAO.save(1, 2);
    }
}