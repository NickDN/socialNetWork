package ru.myproject.dyakins.util.validators.account;

import ru.myproject.dyakins.account.Account;

public class WorkAddressValidator implements Validate {
    private static final int TEXT_LENGTH = 45;

    @Override
    public String validate(Account account) {
        String msg = "";
        String workAddress = account.getWorkAddress();
        if (workAddress == null || workAddress.trim().isEmpty()) {
           return msg;
        }
        if  (workAddress.trim().length() > TEXT_LENGTH) {
            msg = "Work address contains more than " + TEXT_LENGTH + " symbols!";
        }
        return msg;
    }
}