package ru.myproject.dyakins.dao;

import com.mysql.jdbc.Statement;
import ru.myproject.dyakins.dao.connection.ConnectionFactory;
import ru.myproject.dyakins.dao.exception.DAOException;
import ru.myproject.dyakins.phone.Phone;
import ru.myproject.dyakins.phone.PhoneType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDAOImpl implements PhoneDAO {
    private static final String SELECT_ALL = "SELECT * FROM phones WHERE account_id=?";
    private static final String SELECT_BY_ID = SELECT_ALL + " AND id=?";
    private static final String INSERT = "INSERT INTO phones VALUES (NULL, ?, ?, ?)";
    //todo М. можно без user id
    private static final String UPDATE = "UPDATE phones SET number=?, type=? WHERE account_id=? AND id=?";
    private static final String DELETE_BY_ID = "DELETE FROM phones WHERE account_id=? AND id=?";

    @Override
    public Phone get(int id, int accountId) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, accountId);
            ps.setInt(2, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return createPhoneFromResult(resultSet);
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
    public List<Phone> getAll(int accountId) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Phone> phones = new ArrayList<>();
                while (rs.next()) {
                    phones.add(createPhoneFromResult(rs));
                }
                return phones;
            }
        } catch (SQLException e) {
            throw new DAOException(e.getLocalizedMessage());
        } finally {
            ConnectionFactory.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public Phone save(Phone phone, int accountId) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        if (phone.isNew()) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                setValuesInDatabase(phone, accountId, ps);
                if (ps.executeUpdate() == 1) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        int newId = rs.getInt(1);
                        phone.setId(newId);
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(e.getLocalizedMessage());
            } finally {
                ConnectionFactory.getInstance().releaseConnection(connection);
            }
        } else {
            try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
                setValuesInDatabase(phone, accountId, ps);
                ps.setInt(4, phone.getId());
                if (ps.executeUpdate() == 0) {
                    return null;
                }
            } catch (SQLException e) {
                throw new DAOException(e.getLocalizedMessage());
            } finally {
                ConnectionFactory.getInstance().releaseConnection(connection);
            }
        }
        return phone;
    }

   /* @Override
    public boolean update(Phone phone, int accountId) {
        if (!phone.isNew()) {
            Connection connection = ConnectionFactory.getInstance().getConnection();
            try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
                setValuesInDatabase(phone, accountId, ps);
                ps.setInt(4, phone.getId());
                if (ps.executeUpdate() == 1) {
                    return true;
                }
            } catch (SQLException e) {
                throw new DAOException(e.getLocalizedMessage());
            } finally {
                ConnectionFactory.getInstance().releaseConnection(connection);
            }
        }
        return false;
    }*/

    @Override
    public boolean delete(int id, int accountId) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, accountId);
            ps.setInt(2, id);
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e.getLocalizedMessage());
        } finally {
            ConnectionFactory.getInstance().releaseConnection(connection);
        }
        return false;
    }

    private Phone createPhoneFromResult(ResultSet rs) throws SQLException {
        Phone phone = new Phone();
        phone.setId(rs.getInt("id"));
        phone.setType(PhoneType.valueOf(rs.getString("type")));
        phone.setNumber(rs.getLong("number"));
        return phone;
    }

    private void setValuesInDatabase(Phone phone, int accountId, PreparedStatement ps) throws SQLException {
        ps.setLong(1, phone.getNumber());
        ps.setString(2, String.valueOf(phone.getType()));
        ps.setInt(3, accountId);
    }
}