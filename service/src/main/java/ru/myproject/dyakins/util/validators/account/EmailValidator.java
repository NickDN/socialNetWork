package ru.myproject.dyakins.util.validators.account;

import ru.myproject.dyakins.account.Account;

public class EmailValidator implements Validate {
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*"
            + "([A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final int EMAIL_LENGTH = 45;

    @Override
    public String validate(Account account) {
        String msg = "";
        String email = account.getEmail();
        if (email == null || email.trim().isEmpty()) {
            msg = "Email field is empty!";
        } else if (!email.matches(EMAIL_REGEX)) {
            msg = "Incorrect email form!";
        } else if (email.trim().length() > EMAIL_LENGTH) {
            msg = "Email contains more than " + EMAIL_LENGTH + " symbols!";
        }
        return msg;
    }
}