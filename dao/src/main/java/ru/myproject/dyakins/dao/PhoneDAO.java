package ru.myproject.dyakins.dao;

import ru.myproject.dyakins.phone.Phone;

import java.util.List;

public interface PhoneDAO {
    Phone get (int id, int accountId);

    List<Phone> getAll(int accountId);

    Phone save(Phone phone, int accountId);

   // boolean update(Phone phone, int accountId);

    boolean delete(int id, int accountId);
}