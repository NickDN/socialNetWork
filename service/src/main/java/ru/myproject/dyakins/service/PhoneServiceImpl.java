package ru.myproject.dyakins.service;

import ru.myproject.dyakins.dao.PhoneDAO;
import ru.myproject.dyakins.dao.PhoneDAOImpl;
import ru.myproject.dyakins.phone.Phone;

import java.util.List;

import static ru.myproject.dyakins.util.ValidationUtil.checkNotFoundWithId;

public class PhoneServiceImpl implements PhoneService {

    private PhoneDAO phoneDAO;

    public PhoneServiceImpl() {
        this.phoneDAO = new PhoneDAOImpl();
    }

    @Override
    public Phone get(int id, int accountId) {
        return checkNotFoundWithId(phoneDAO.get(id, accountId), id);
    }

    @Override
    public List<Phone> getAll(int accountId) {
        return phoneDAO.getAll(accountId);
    }

    @Override
    public void delete(int id, int accountId) {
        checkNotFoundWithId(phoneDAO.delete(id, accountId), id);
    }

    @Override
    public Phone create(Phone phone, int accountId) {
        return phoneDAO.save(phone, accountId);
    }

    @Override
    public void update(Phone phone, int accountId) {
        checkNotFoundWithId(phoneDAO.save(phone, accountId), phone.getId());
    }
}