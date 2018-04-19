package ru.myproject.dyakins.util.validators.account;

import ru.myproject.dyakins.account.Account;

public class HomeAddressValidator implements Validate {
    private static final int TEXT_LENGTH = 45;

    @Override
    public String validate(Account account) {
        String msg = "";
        String homeAddress = account.getHomeAddress();
        if (homeAddress == null || homeAddress.trim().isEmpty()) {
           return msg;
        }
        if  (homeAddress.trim().length() > TEXT_LENGTH) {
            msg = "Home address contains more than " + TEXT_LENGTH + " symbols!";
        }
        return msg;
    }
}