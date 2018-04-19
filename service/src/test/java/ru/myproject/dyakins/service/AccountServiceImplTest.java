package ru.myproject.dyakins.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.myproject.dyakins.account.Account;
import ru.myproject.dyakins.dao.AccountDAO;
import ru.myproject.dyakins.util.exception.NotFoundException;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private List<Account> accounts;
    @Mock
    private AccountDAO accountDAO;
    @InjectMocks
    private AccountService accountService = new AccountServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        accounts = new ArrayList<>(Arrays.asList(
                new Account
                        .AccountBuilder("Ivan", "Stolov", LocalDate.of(1989, 8, 10), "IvanStolov@mail.ru", "123")
                        .setId(1)
                        .build(),
                new Account
                        .AccountBuilder("Alla", "Petrova", LocalDate.of(1990, 5, 15), "Alla1990@yandex.ru", "123")
                        .setId(2)
                        .build()));
    }

    @Test
    public void get() {
        when(accountDAO.get(1)).thenReturn(accounts.get(0));
        assertEquals(accounts.get(0), accountService.get(1));
        verify(accountDAO, times(1)).get(1);
    }

    @Test
    public void getAll() {
        when(accountDAO.getAll()).thenReturn(accounts);
        assertEquals(accounts, accountService.getAll());
        verify(accountDAO, times(1)).getAll();
    }

    @Test
    public void delete() {
        when(accountDAO.delete(anyInt())).thenReturn(true);
        accountService.delete(1);
        verify(accountDAO, times(1)).delete(1);
    }

    @Test
    public void deleteThrowException() {
        //Given
        when(accountDAO.delete(anyInt())).thenReturn(false);
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with setId=1");

        //When
        accountService.delete(1);

        //Then
        verify(accountDAO, times(1)).delete(1);
    }

    //todo Разобраться с ошибкой
    @Test
    public void create() throws ValidationException {
        Account newAccount = new Account
                .AccountBuilder("Aleksandr", "Parkov", LocalDate.of(1991, 1, 25), "ParkovA@gmail.com", "123")
                .build();
        when(accountDAO.save(newAccount)).then(a -> {
            newAccount.setId(99);
            return newAccount;
        });
        assertEquals(new Integer(99), accountService.create(newAccount).getId());
        verify(accountDAO, times(1)).save(any(Account.class));
    }

    @Test
    public void update() throws ValidationException {
        Account updatedAccount = new Account
                .AccountBuilder("Aleksandr", "Parkov", LocalDate.of(1991, 1, 25), "ParkovA@gmail.com", "123")
                .setId(99)
                .build();
        when(accountDAO.save(updatedAccount)).then(a -> {
            updatedAccount.setFirstName("UpdatedName");
            return updatedAccount;
        });
        accountService.update(updatedAccount);
        assertEquals("UpdatedName", updatedAccount.getFirstName());
        verify(accountDAO, times(1)).save(any(Account.class));
    }

    @Test
    public void updateThrowException() throws ValidationException {
        Account updatedAccount = new Account
                .AccountBuilder("Aleksandr", "Parkov", LocalDate.of(1991, 1, 25), "ParkovA@gmail.com", "123")
                .setId(99)
                .build();
        when(accountDAO.save(any(Account.class))).thenReturn(null);
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with setId=99");
        accountService.update(updatedAccount);
        verify(accountDAO, times(1)).save(any(Account.class));
    }
}