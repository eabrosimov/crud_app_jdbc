package org.example;

import org.example.model.Developer;
import org.example.repository.jdbc.JdbcDeveloperRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DeveloperServiceTest {

    @Mock
    JdbcDeveloperRepositoryImpl jdbcDeveloperRepositoryMock;

    @Test
    public void save(){
        Developer developerFromMock = new Developer();
        Mockito.when(jdbcDeveloperRepositoryMock.save(developerFromMock)).thenReturn(developerFromMock);
        Developer developer = jdbcDeveloperRepositoryMock.save(developerFromMock);
        Assertions.assertEquals(developerFromMock, developer);
    }

    @Test
    public void update(){
        Developer developerFromMock = new Developer();
        Mockito.when(jdbcDeveloperRepositoryMock.update(developerFromMock)).thenReturn(developerFromMock);
        Developer developer = jdbcDeveloperRepositoryMock.update(developerFromMock);
        Assertions.assertEquals(developer, developerFromMock);
    }

    @Test
    public void getAllTest(){
        List<Developer> developersFromMock = new ArrayList<>();
        Mockito.when(jdbcDeveloperRepositoryMock.getAll()).thenReturn(developersFromMock);
        List<Developer> developers = jdbcDeveloperRepositoryMock.getAll();
        Assertions.assertEquals(developers, developersFromMock);
    }

    @Test
    public void getById(){
        Integer integer1 = 1;
        Integer integer2 = 2;
        Developer developerFromMock = new Developer();
        Mockito.when(jdbcDeveloperRepositoryMock.getById(integer1)).thenReturn(developerFromMock);
        Mockito.when(jdbcDeveloperRepositoryMock.getById(integer2)).thenReturn(null);
        Developer developer1 = jdbcDeveloperRepositoryMock.getById(integer1);
        Developer developer2 = jdbcDeveloperRepositoryMock.getById(integer2);
        Assertions.assertEquals(developer1, developerFromMock);
        Assertions.assertNull(developer2);
    }

    @Test
    public void deleteById(){
        Integer integer1 = 1;
        Integer integer2 = 2;
        Mockito.when(jdbcDeveloperRepositoryMock.deleteById(integer1)).thenReturn(true);
        Mockito.when(jdbcDeveloperRepositoryMock.deleteById(integer2)).thenReturn(false);
        boolean b1 = jdbcDeveloperRepositoryMock.deleteById(integer1);
        boolean b2 = jdbcDeveloperRepositoryMock.deleteById(integer2);
        Assertions.assertTrue(b1);
        Assertions.assertFalse(b2);
    }

}
