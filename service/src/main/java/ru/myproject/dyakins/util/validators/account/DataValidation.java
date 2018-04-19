package ru.myproject.dyakins.util.validators.account;

import ru.myproject.dyakins.account.Account;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//todo так можно?
public class DataValidation {
    public void validate(Account account, boolean onlyRegistrationInfo) throws ValidationException {
        List<Validate> generalValidator = new ArrayList<>(Arrays.asList(new FirstSecondNameValidator(),
                new EmailValidator(), new DateOfBirthValidator(), new PasswordValidator()));
        StringBuilder validationMsg = new StringBuilder();
        String msg;
        for (Validate v : generalValidator) {
            msg = v.validate(account);
            if (!msg.isEmpty()) {
                validationMsg.append(msg);
            }
        }
        if (!onlyRegistrationInfo) {
            List<Validate> addValidator = new ArrayList<>(Arrays.asList(new MiddleNameValidator(), new IcqValidator(),
                    new HomeAddressValidator(), new WorkAddressValidator(), new SkypeValidator(),
                    new AdditionalInfoValidator()));
            for (Validate v : addValidator) {
                msg = v.validate(account);
                if (!msg.isEmpty()) {
                    validationMsg.append(msg);
                }
            }
        }
        String errorMsg = validationMsg.toString();
        if (!errorMsg.isEmpty()) {
            throw new ValidationException(errorMsg);
        }
    }
}