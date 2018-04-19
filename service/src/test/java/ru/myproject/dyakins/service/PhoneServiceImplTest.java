package ru.myproject.dyakins.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.myproject.dyakins.dao.PhoneDAO;
import ru.myproject.dyakins.phone.Phone;
import ru.myproject.dyakins.phone.PhoneType;
import ru.myproject.dyakins.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PhoneServiceImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private List<Phone> phones;
    @Mock
    private PhoneDAO phoneDAO;
    @InjectMocks
    private PhoneService phoneService = new PhoneServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        phones = new ArrayList<>(Arrays.asList(
                new Phone(1, PhoneType.HOME, 87589632457L),
                new Phone(2, PhoneType.WORK, 89158742574L)));
    }

    @Test
    public void get() {
        when(phoneDAO.get(1,1)).thenReturn(phones.get(0));
        assertEquals(phones.get(0), phoneService.get(1,1));
        verify(phoneDAO, times(1)).get(1,1);
    }

    @Test
    public void getAll() {
        when(phoneDAO.getAll(1)).thenReturn(phones);
        assertEquals(phones, phoneService.getAll(1));
        verify(phoneDAO, times(1)).getAll(1);
    }

   @Test
    public void delete() {
        when(phoneDAO.delete(anyInt(),anyInt())).thenReturn(true);
        phoneService.delete(1,1);
        verify(phoneDAO, times(1)).delete(anyInt(),anyInt());
    }

    @Test
    public void deleteThrowException() {
        when(phoneDAO.delete(anyInt(),anyInt())).thenReturn(false);
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with setId=1");
        phoneService.delete(1,1);
        verify(phoneDAO, times(1)).delete(anyInt(),anyInt());
    }

    @Test
    public void create() {
        Phone newPhone = new Phone(PhoneType.WORK, 89158749999L);
        when(phoneDAO.save(newPhone,1)).then(p->{
            newPhone.setId(99);
            return newPhone;
        });
        assertEquals(new Integer(99), phoneService.create(newPhone,1).getId());
        verify(phoneDAO, times(1)).save(any(Phone.class),anyInt());
    }

    @Test
    public void update() {
        Phone updatedPhone = new Phone(3,PhoneType.WORK, 89158749999L);
        when(phoneDAO.save(updatedPhone,1)).then(p->{
            updatedPhone.setNumber(89158740000L);
            return updatedPhone;
        });
        phoneService.update(updatedPhone,1);
        assertEquals(new Long(89158740000L), updatedPhone.getNumber());
        verify(phoneDAO, times(1)).save(any(Phone.class),anyInt());
    }

    @Test
    public void updateThrowException() {
        Phone updatedPhone = new Phone(3,PhoneType.WORK, 89158749999L);
        when(phoneDAO.save(any(Phone.class),anyInt())).thenReturn(null);
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with setId=3");
        phoneService.update(updatedPhone,1);
        verify(phoneDAO, times(1)).save(any(Phone.class),anyInt());
    }
}