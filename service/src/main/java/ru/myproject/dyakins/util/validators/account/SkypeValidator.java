package ru.myproject.dyakins.util.validators.account;

import ru.myproject.dyakins.account.Account;

public class SkypeValidator implements Validate {
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*"
            + "([A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final int SKYPE_LENGTH = 45;

    @Override
    public String validate(Account account) {
        String msg = "";
        String skype = account.getSkype();
        if (skype == null || skype.trim().isEmpty()) {
           return msg;
        } else if (skype.contains("@") && !skype.matches(EMAIL_REGEX)) {
            msg = "Incorrect skype form!";
        } else if (skype.trim().length() > SKYPE_LENGTH) {
            msg = "Skype number contains more than " + SKYPE_LENGTH + " symbols!";
        }
        return msg;
    }
}