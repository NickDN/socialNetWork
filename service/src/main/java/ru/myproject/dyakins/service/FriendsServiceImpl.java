package ru.myproject.dyakins.service;

import ru.myproject.dyakins.dao.FriendsDAO;

import java.util.List;

import static ru.myproject.dyakins.util.ValidationUtil.checkNotFoundWithId;

public class FriendsServiceImpl implements FriendsService {

    private FriendsDAO friendsDAO;

    public FriendsServiceImpl(FriendsDAO friendsDAO) {
        this.friendsDAO = friendsDAO;
    }

    @Override
    public List<Integer> getAll(int accountId) {
        return friendsDAO.getAll(accountId);
    }

    @Override
    public void save(int accountId, int friendId) {
        checkNotFoundWithId(friendsDAO.save(accountId, friendId),friendId);
    }

    @Override
    public void delete(int accountId, int friendId) {
        checkNotFoundWithId(friendsDAO.delete(accountId, friendId), friendId);
    }
}