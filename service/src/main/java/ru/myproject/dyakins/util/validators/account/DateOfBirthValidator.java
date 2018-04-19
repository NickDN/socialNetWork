package ru.myproject.dyakins.util.validators.account;

import ru.myproject.dyakins.account.Account;

public class DateOfBirthValidator implements Validate {

    @Override
    public String validate(Account account) {
        String msg = "";
        if (account.getDateOfBirth() == null) {
            msg = "Birthday field is empty!";
        }
        return msg;
    }
}