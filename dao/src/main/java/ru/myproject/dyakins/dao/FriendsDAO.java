package ru.myproject.dyakins.dao;

import java.util.List;

public interface FriendsDAO {
    boolean save(int accountId, int friendId);

    boolean delete(int accountId, int friendId);

    List<Integer> getAll(int accountId);
}