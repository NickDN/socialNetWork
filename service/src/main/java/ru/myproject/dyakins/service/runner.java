package ru.myproject.dyakins.service;

import ru.myproject.dyakins.account.Account;
import ru.myproject.dyakins.dao.AccountDAOImpl;
import ru.myproject.dyakins.util.Password;

public class runner {
    public static void main(String[] args) {
        AccountDAOImpl dao = new AccountDAOImpl();
//$2a$10$iJ9RmNzE8YOp/RW6hbXyLOcQlHY7MXFY5l2rhEvGV2SHv91MdlULS   123
        AccountService accountService = new AccountServiceImpl();
        Account account = accountService.get("is@yandex.ru");
        System.out.println(account);
        System.out.println(account.getPassword());

        System.out.println(Password.getHashPassword("123123123qQ"));
        /*Account account = new Account();
        account.setFirstName("Ivan");
        account.setSecondName("Ivanov");
        account.setDateOfBirth(LocalDate.of(2000, 8, 12));
        account.setEmail("Ivanov12000@mail.ru");*/

        //dao.delete(11);
        //System.out.println(dao.save(account));
        //  dao.closeConnection();

        // accountService.delete(2);
    }
}
