package ru.myproject.dyakins.dao;

import ru.myproject.dyakins.account.Account;

import java.util.List;

public interface AccountDAO {
    Account get(int id);

    Account get (String email);

    List<Account> getAll();

    Account save(Account account);

    //boolean update(Account account);

    boolean delete(int id);
}