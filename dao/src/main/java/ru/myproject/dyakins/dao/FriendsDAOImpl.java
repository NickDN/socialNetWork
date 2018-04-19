package ru.myproject.dyakins.dao;

import ru.myproject.dyakins.dao.connection.ConnectionFactory;
import ru.myproject.dyakins.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//todo (доделать) реализовано без запросов на дружбу, подтверждения дружбы и отклонения дружбы
public class FriendsDAOImpl implements FriendsDAO {
    private static final String SELECT_ALL = "SELECT * FROM friends WHERE (account_one_id=? OR account_two_id=?) AND status=1";
    private static final String INSERT = "INSERT INTO friends VALUES (?,?,1,?)";
    private static final String DELETE = "DELETE FROM friends WHERE account_one_id=? AND account_two_id=?";

    @Override
    public boolean save(int accountId, int friendId) {
        int firstAccount = accountId;
        int secondAccount = friendId;
        int actionAccount = accountId;
        if (secondAccount < firstAccount) {
            firstAccount = secondAccount;
            secondAccount = actionAccount;
            accountId = firstAccount;
        }
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setInt(1, firstAccount);
            ps.setInt(2, secondAccount);
            ps.setInt(3, accountId);
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

    @Override
    public boolean delete(int accountId, int friendId) {
        int firstAccount = accountId;
        int secondAccount = friendId;
        if (secondAccount < firstAccount) {
            int temp = firstAccount;
            firstAccount = secondAccount;
            secondAccount = temp;
        }
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setInt(1, firstAccount);
            ps.setInt(2, secondAccount);
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

    //todo Э Чтобы на уровне DAO  не вызывать AccountDAO (получение аккаунта по setId) собираю setId друзей, которые будут "переведены" в аккаунты на service слое
    @Override
    public List<Integer> getAll(int accountId) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL)) {
            ps.setInt(1, accountId);
            ps.setInt(2, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Integer> friends = new ArrayList<>();
                while (rs.next()) {
                    friends.add(findFriendId(rs, accountId));
                }
                return friends;
            }
        } catch (SQLException e) {
            throw new DAOException(e.getLocalizedMessage());
        } finally {
            ConnectionFactory.getInstance().releaseConnection(connection);
        }
    }

    private Integer findFriendId(ResultSet rs, int accountId) throws SQLException {
        Integer friendId = rs.getInt("account_one_id");
        if (friendId == accountId) {
            friendId = rs.getInt("account_two_id");
        }
        return friendId;
    }
}