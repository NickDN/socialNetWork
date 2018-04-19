package ru.myproject.dyakins.util.validators.account;

import ru.myproject.dyakins.account.Account;

public class IcqValidator implements Validate {
    private static final String ICQ_REGEX = "[0-9]{5,9}$";

    @Override
    public String validate(Account account) {
        String msg = "";
        String icq = account.getIcq();
        if (icq == null || icq.trim().isEmpty()) {
            return msg;
        } else if (!icq.matches(ICQ_REGEX)) {
            msg = "Incorrect ICQ form!";
        }
        return msg;
    }
}
