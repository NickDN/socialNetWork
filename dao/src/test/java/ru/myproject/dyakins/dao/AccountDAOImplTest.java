package ru.myproject.dyakins.dao;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
import ru.myproject.dyakins.account.Account;
import ru.myproject.dyakins.dao.connection.ConnectionFactory;
import ru.myproject.dyakins.dao.exception.DAOException;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccountDAOImplTest {
    private Statement statement;
    private AccountDAO accountDAO = new AccountDAOImpl();

    @BeforeClass
    public static void createDataBase() throws IOException {
        Connection con = ConnectionFactory.getInstance().getConnection();
        //InputStream scriptFilePath = AccountDAOImplTest.class.getClassLoader().getResourceAsStream("initDB.sql");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(AccountDAOImplTest.class.getClassLoader().getResourceAsStream("initDB.sql"),"UTF-8"))) {
            //todo Э!. Скрипты из файла
            Statement st = con.createStatement();
           ScriptRunner sr = new ScriptRunner(con);
            sr.runScript(reader);
           /* String line;
            while ((line = reader.readLine()) != null) {
                st.execute(line);
            }*/
            /*st.executeUpdate("CREATE TABLE accounts (" +
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
                    "  additionalInfo VARCHAR(45) NULL," +
                    "  password VARCHAR(15) NOT NULL);");*/
            if (!st.isClosed()) {
                st.close();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.getInstance().releaseConnection(con);
        }
    }

    @AfterClass
    public static void deleteDataBase() {
        Connection con = ConnectionFactory.getInstance().getConnection();
        try {
            Statement st = con.createStatement();
            st.executeUpdate("DROP TABLE accounts;");
            if (!st.isClosed()) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.getInstance().releaseConnection(con);
            ConnectionFactory.getInstance().shutdown();
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
                    "NULL, NULL, NULL,'123');");
            statement.addBatch("INSERT INTO accounts VALUES " +
                    "(NULL, 'Alla', 'Petrovna', 'Sergeevna', 'Female', '1999-08-07', 'Moscow', 'Moscow', " +
                    "'Alla1999@yandex.ru', NULL, NULL, NULL,'123');");
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

    //todo переделать не обращаться дважды
    @Test
    public void get() {
        String actual = this.accountDAO.get(2).getEmail();
        String expected = "Alla1999@yandex.ru";
        assertEquals(expected, actual);
        actual = this.accountDAO.get(2).getFirstName();
        expected = "Alla";
        assertEquals(expected, actual);
    }

    @Test
    public void getAll() {
        List<Account> actual = this.accountDAO.getAll();
        assertEquals(2, actual.size());
        assertEquals("Alla1999@yandex.ru", actual.get(1).getEmail());
    }

    //todo смотреть результат удаления
    @Test
    public void delete() {
        this.accountDAO.delete(1);
        Account account = this.accountDAO.get(1);
        assertNull(account);
    }

    @Test
    public void saveNewAccount() {
        Account account = new Account
                .AccountBuilder("Ivan", "Ivanov", LocalDate.of(2000, 8, 12), "Ivanov2000@mail.ru", "123")
                .build();
        this.accountDAO.save(account);
        String actual = this.accountDAO.get(3).getEmail();
        String expected = "Ivanov2000@mail.ru";
        assertEquals(expected, actual);
        actual = this.accountDAO.get(3).getFirstName();
        expected = "Ivan";
        assertEquals(expected, actual);
    }

    @Test(expected = DAOException.class)
    public void saveNotUniqueEmail() {
        Account account = new Account
                .AccountBuilder("Ivan", "Ivanov", LocalDate.of(2000, 8, 12), "PIP@yandex.ru", "123")
                .build();
        this.accountDAO.save(account);
    }

    @Test
    public void saveUpdatedAccount() {
        Account account = new Account
                .AccountBuilder("Ivan", "Ivanov", LocalDate.of(2000, 8, 12), "Ivanov2000@mail.ru", "123")
                .setId(1)
                .build();
        this.accountDAO.save(account);
        String actual = this.accountDAO.get(1).getEmail();
        String expected = "Ivanov2000@mail.ru";
        assertEquals(expected, actual);
        actual = this.accountDAO.get(1).getFirstName();
        expected = "Ivan";
        assertEquals(expected, actual);
    }
}