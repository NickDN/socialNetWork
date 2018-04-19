package ru.myproject.dyakins.util.validators.account;

import ru.myproject.dyakins.account.Account;

public class FirstSecondNameValidator implements Validate {
    private static final String LETTERS_REGEX = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
    private static final String UNICODE_REGEX = "^\\pL+[\\pL\\pZ\\pP]{0,}$";
    private static final int TEXT_LENGTH = 45;

    @Override
    public String validate(Account account) {
        StringBuilder msg = new StringBuilder();
        String firstName = account.getFirstName();
        String secondName = account.getSecondName();
        if (firstName == null || firstName.trim().isEmpty()) {
            msg.append("First name field is empty!");
        } else if (!firstName.matches(LETTERS_REGEX) || !firstName.matches(UNICODE_REGEX)) {
            msg.append("First name contains unknown symbols!");
        } else if (firstName.trim().length() > TEXT_LENGTH) {
            msg.append("First name contains more than " + TEXT_LENGTH + " symbols!");
        }

        if (secondName == null || secondName.trim().isEmpty()) {
            msg.append("Second name field is empty!");
        } else if (!secondName.matches(LETTERS_REGEX) || !secondName.matches(UNICODE_REGEX)) {
            msg.append("Second name contains unknown symbols!");
        } else if (secondName.trim().length() > TEXT_LENGTH) {
            msg.append("Second name contains more than " + TEXT_LENGTH + " symbols!");
        }
        return msg.toString();
    }
}