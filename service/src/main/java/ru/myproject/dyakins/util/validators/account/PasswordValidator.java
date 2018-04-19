package ru.myproject.dyakins.util.validators.account;

import ru.myproject.dyakins.account.Account;

public class PasswordValidator implements Validate {
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    @Override
    public String validate(Account account) {
        String msg = "";
        String password = account.getPassword();
        if (password == null || password.trim().isEmpty()) {
            msg = "Password field is empty!";
        } else if (!password.matches(PASSWORD_REGEX)) {
            msg = "Password has to contain at least 8 chars: 0-9, aA-zZ";
        }
        return msg;
    }
}
