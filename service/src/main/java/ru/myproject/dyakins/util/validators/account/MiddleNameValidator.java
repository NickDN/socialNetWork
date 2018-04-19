package ru.myproject.dyakins.util.validators.account;

import ru.myproject.dyakins.account.Account;

public class MiddleNameValidator implements Validate {
    private static final String LETTERS_REGEX = "^[A-Za-z\\s]+[.]?[A-Za-z\\s]*$";
    private static final String UNICODE_REGEX = "^\\pL+[\\pL\\pZ\\pP]*$";
    private static final int TEXT_LENGTH = 45;

    @Override
    public String validate(Account account) {
        String msg = "";
        String middleName = account.getMiddleName();
        if (middleName == null || middleName.trim().isEmpty()) {
           return msg;
        }
        if (!middleName.matches(LETTERS_REGEX) || !middleName.matches(UNICODE_REGEX)) {
            msg = "Middle name contains unknown symbols!";
        } else if (middleName.trim().length() > TEXT_LENGTH) {
            msg = "Middle name contains more than " + TEXT_LENGTH + " symbols!";
        }
        return msg;
    }
}