package ru.myproject.dyakins.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.myproject.dyakins.dao.connection.ConnectionFactory;
import ru.myproject.dyakins.dao.exception.DAOException;
import ru.myproject.dyakins.phone.Phone;
import ru.myproject.dyakins.phone.PhoneType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class PhoneDAOImplTest {
    private Statement statement;
    private PhoneDAO phoneDAO = new PhoneDAOImpl();

    @BeforeClass
    public static void createDataBase() {
        Connection con = ConnectionFactory.getInstance().getConnection();
        try {
            Statement st = con.createStatement();
            st.addBatch("CREATE TABLE accounts (" +
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

            st.addBatch("CREATE TABLE IF NOT EXISTS phones (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  number BIGINT(20) NOT NULL UNIQUE," +
                    "  type VARCHAR(6) NOT NULL," +
                    "  account_id INT NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  CONSTRAINT fk_Phone_Account" +
                    "    FOREIGN KEY (account_id)" +
                    "    REFERENCES accounts (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE NO ACTION)");
            st.executeBatch();
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
            statement.executeBatch();
            statement.addBatch("INSERT INTO phones VALUES (NULL, 89158746200, 'WORK', 1 );");
            statement.addBatch("INSERT INTO phones VALUES (NULL, 84957865264, 'HOME', 1 );");
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
            statement.addBatch("ALTER TABLE phones ALTER COLUMN id RESTART WITH 1");
            statement.executeBatch();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void get() {
        assertEquals(new Long(89158746200L), phoneDAO.get(1, 1).getNumber());
    }

    @Test
    public void getAll() {
        List<Phone> actual = phoneDAO.getAll(1);
        assertEquals(2, actual.size());
        assertEquals(new Long(84957865264L), actual.get(1).getNumber());
    }

    @Test
    public void delete() {
        phoneDAO.delete(1, 1);
        Phone phone = phoneDAO.get(1, 1);
        assertNull(phone);
    }

    @Test
    public void saveNewAccount() {
        Phone phone = new Phone(PhoneType.HOME, 87589632457L);
        phoneDAO.save(phone, 1);
        Long actual = phoneDAO.get(3, 1).getNumber();
        Long expected = 87589632457L;
        assertEquals(expected, actual);
    }

    @Test(expected = DAOException.class)
    public void saveNotUniquePhone() {
        Phone phone = new Phone(PhoneType.HOME, 89158746200L);
        phoneDAO.save(phone, 1);
    }

    @Test
    public void saveAccountUpdates() {
        Phone phone = new Phone(PhoneType.HOME, 87589632457L);
        phone.setId(1);
        phoneDAO.save(phone, 1);
        Long actual = phoneDAO.get(1, 1).getNumber();
        Long expected = 87589632457L;
        assertEquals(expected, actual);
    }
}