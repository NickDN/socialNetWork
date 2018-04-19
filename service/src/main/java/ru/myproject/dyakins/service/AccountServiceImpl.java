package ru.myproject.dyakins.service;

import ru.myproject.dyakins.account.Account;
import ru.myproject.dyakins.dao.AccountDAO;
import ru.myproject.dyakins.dao.AccountDAOImpl;
import ru.myproject.dyakins.dao.exception.DAOException;
import ru.myproject.dyakins.util.Password;
import ru.myproject.dyakins.util.validators.account.DataValidation;

import javax.xml.bind.ValidationException;
import java.util.List;

import static ru.myproject.dyakins.util.ValidationUtil.checkNotFoundWithEmail;
import static ru.myproject.dyakins.util.ValidationUtil.checkNotFoundWithId;

public class AccountServiceImpl implements AccountService {
    private static final String NOT_UNIQUE_SQL_CODE = "1062";

    private AccountDAO accountDAO;

    public AccountServiceImpl() {
        this.accountDAO = new AccountDAOImpl();
    }

    @Override
    public Account get(int id) {
        return checkNotFoundWithId(accountDAO.get(id), id);
    }

    @Override
    public Account get(String email) {
        return checkNotFoundWithEmail(accountDAO.get(email), email);
    }

    @Override
    public List<Account> getAll() {
        return accountDAO.getAll();
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(accountDAO.delete(id), id);
    }

    @Override
    public Account create(Account account) throws ValidationException {
        //todo синхронизировать с update (-> валидация)
        Account regAccount;
        try {
            DataValidation validation = new DataValidation();
            validation.validate(account, true);
            String nonHashPassword = account.getPassword();
            account.setPassword(Password.getHashPassword(nonHashPassword));
            regAccount = accountDAO.save(account);
        } catch (DAOException e) {
            if (e.getMessage().equals(NOT_UNIQUE_SQL_CODE)) {
                throw new ValidationException("Account with this email address already exists");
            } else {
                throw e;
            }
        }
        return regAccount;
    }

    @Override
    public void update(Account account) throws ValidationException {
        try {
            DataValidation validation = new DataValidation();
            validation.validate(account, false);
            checkNotFoundWithId(accountDAO.save(account), account.getId());
        } catch (DAOException e) {
            if (e.getMessage().equals(NOT_UNIQUE_SQL_CODE)) {
                throw new ValidationException("Account with this email address already exists");
            } else {
                throw e;
            }
        }
    }
}