package ru.myproject.dyakins.service;

import ru.myproject.dyakins.phone.Phone;

import java.util.List;

public interface PhoneService {
    Phone get(int id, int accountId);

    List<Phone> getAll(int accountId);

    void delete(int id, int accountId);

    Phone create(Phone phone, int accountId);

    void update(Phone phone, int accountId);
}