package ru.myproject.dyakins.dao;

import ru.myproject.dyakins.account.Account;

public class runner {
    public static void main(String[] args) {
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        FriendsDAOImpl friendsDAO = new FriendsDAOImpl();

        Account account = accountDAO.get(1);
        System.out.println(account);
        /*Account account = new Account();
        account.setFirstName("Ivan");
        account.setSecondName("Ivanov");
        account.setDateOfBirth(LocalDate.of(2000, 8, 12));
        account.setEmail("Ivanov12000@mail.ru");*/

        // dao.delete(11);
        //System.out.println(dao.save(account));
        //  dao.closeConnection();

      // System.out.println(friendsDAO.save(3,4));
        System.out.println();
      //  System.out.println(friendsDAO.getAll(4));
        for (Integer f:friendsDAO.getAll(4)) {
            System.out.println(accountDAO.get(f));
        }
    }
}
