package ru.myproject.dyakins.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.myproject.dyakins.dao.FriendsDAO;
import ru.myproject.dyakins.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FriendsServiceImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private List<Integer> friends;
    @Mock
    private FriendsDAO friendsDAO;
    @InjectMocks
    private FriendsService friendsService = new FriendsServiceImpl(friendsDAO);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        friends = new ArrayList<>(Arrays.asList(2, 3));
    }

    @Test
    public void getAll() {
        when(friendsDAO.getAll(1)).thenReturn(friends);
        assertEquals(friends, friendsService.getAll(1));
        verify(friendsDAO, times(1)).getAll(anyInt());
    }

    @Test
    public void delete() {
        when(friendsDAO.delete(anyInt(), anyInt())).thenReturn(true);
        friendsService.delete(1, 2);
        verify(friendsDAO, times(1)).delete(anyInt(), anyInt());
    }

    @Test
    public void deleteThrowException() {
        when(friendsDAO.delete(anyInt(), anyInt())).thenReturn(false);
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with setId=2");
        friendsService.delete(1, 2);
        verify(friendsDAO, times(1)).delete(anyInt(), anyInt());
    }

    @Test
    public void save() {
        when(friendsDAO.save(anyInt(), anyInt())).thenReturn(true);
        friendsService.save(1,2);
        verify(friendsDAO, times(1)).save(anyInt(), anyInt());
    }
}