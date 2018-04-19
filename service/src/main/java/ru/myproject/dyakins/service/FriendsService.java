package ru.myproject.dyakins.service;

import java.util.List;

public interface FriendsService {
    List<Integer> getAll(int accountId);

    void save(int accountId, int friendId);

    void delete(int accountId, int friendId);
}