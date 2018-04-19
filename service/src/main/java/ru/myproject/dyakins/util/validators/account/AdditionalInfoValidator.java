package ru.myproject.dyakins.util.validators.account;

import ru.myproject.dyakins.account.Account;

public class AdditionalInfoValidator implements Validate {
    private static final int TEXT_LENGTH = 45;

    @Override
    public String validate(Account account) {
        String msg = "";
        String addInfo = account.getAdditionalInfo();
        if (addInfo == null || addInfo.trim().isEmpty()) {
            return msg;
        } else if (addInfo.trim().length() > TEXT_LENGTH) {
            msg = "Additional information field contains more than " + TEXT_LENGTH + " symbols!";
        }
        return msg;
    }
}