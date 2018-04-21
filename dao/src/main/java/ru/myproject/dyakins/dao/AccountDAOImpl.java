package ru.myproject.dyakins.dao;

import ru.myproject.dyakins.account.Account;
import ru.myproject.dyakins.account.Gender;
import ru.myproject.dyakins.dao.connection.ConnectionFactory;
import ru.myproject.dyakins.dao.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {
    private static final String SELECT_ALL = "SELECT * FROM socialDB.accounts";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE id=?";
    private static final String SELECT_BY_EMAIL = SELECT_ALL + " WHERE email=?";
    private static final String INSERT =
            "INSERT INTO socialDB.accounts (firstName, secondname,middlename,gender,birthdate,homeaddress,workaddress,email,icq,skype,additionalinfo,password )" +
                    " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
    private static final String UPDATE = "UPDATE socialDB.accounts(firstName,secondname,middleName,gender)" +
            " SET firstName=?, secondName=?, middleName=?, gender=?," +
            "birthDate=?, homeAddress=?, workAddress=?, email=?, icq=?, skype=?, additionalInfo=?, password=? WHERE id=?";
    private static final String DELETE_BY_ID = "DELETE FROM socialDB.accounts WHERE id=?";

    @Override
    public Account get(int id) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return createAccountFromResult(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e.getLocalizedMessage());
        } finally {
            ConnectionFactory.getInstance().releaseConnection(connection);
        }
    }

    //todo Test
    @Override
    public Account get(String email) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return createAccountFromResult(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException(e.getLocalizedMessage());
        } finally {
            ConnectionFactory.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public List<Account> getAll() {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try (ResultSet rs = connection.createStatement().executeQuery(SELECT_ALL)) {
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                accounts.add(createAccountFromResult(rs));
            }
            return accounts;
        } catch (SQLException e) {
            throw new DAOException(e.getLocalizedMessage());
        } finally {
            ConnectionFactory.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public Account save(Account account) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        if (account.isNew()) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                setValuesInDatabase(account, ps);
                if (ps.executeUpdate() == 1) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        int newId = rs.getInt(1);
                        account.setId(newId);
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            } finally {
                ConnectionFactory.getInstance().releaseConnection(connection);
            }
        } else {
            try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
                setValuesInDatabase(account, ps);
                ps.setInt(13, account.getId());
                if (ps.executeUpdate() == 0) {
                    return null;
                }
            } catch (SQLException e) {
                throw new DAOException(String.valueOf(e.getErrorCode()));
            } finally {
                ConnectionFactory.getInstance().releaseConnection(connection);
            }
        }
        return account;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 1) {
                // this.connection.commit();
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e.getLocalizedMessage());
        } finally {
            ConnectionFactory.getInstance().releaseConnection(connection);
        }
        return false;
    }

    private Account createAccountFromResult(ResultSet rs) throws SQLException {
        return new Account
                .AccountBuilder(rs.getString("firstName"), rs.getString("secondName"),
                rs.getDate("birthDate").toLocalDate(), rs.getString("email"), rs.getString("password"))
                .setId(rs.getInt("id"))
                .setMiddleName(rs.getString("middleName"))
                .setGender(checkGender(rs.getString("gender")))
                .setHomeAddress(rs.getString("homeAddress"))
                .setWorkAddress(rs.getString("workAddress"))
                .setIcq(rs.getString("icq"))
                .setSkype(rs.getString("skype"))
                .setAdditionalInfo(rs.getString("additionalInfo"))
                .build();
    }

    private Gender checkGender(String str) {
        return str.equals("null") ? Gender.UNKNOWN : Gender.valueOf(str.toUpperCase());
    }

    private void setValuesInDatabase(Account account, PreparedStatement ps) throws SQLException {
        ps.setString(1, account.getFirstName());
        ps.setString(2, account.getSecondName());
        ps.setString(3, account.getMiddleName());
        ps.setString(4, String.valueOf(account.getGender()).toLowerCase());
        ps.setDate(5, Date.valueOf(account.getDateOfBirth()));
        ps.setString(6, account.getHomeAddress());
        ps.setString(7, account.getWorkAddress());
        ps.setString(8, account.getEmail());
        ps.setString(9, account.getIcq());
        ps.setString(10, account.getSkype());
        ps.setString(11, account.getAdditionalInfo());
        ps.setString(12, account.getPassword());
    }
}