package org.example;

import org.example.model.Developer;
import org.example.repository.jdbc.JdbcDeveloperRepositoryImpl;
import org.example.service.DeveloperService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DeveloperServiceTest {

    @Mock
    private JdbcDeveloperRepositoryImpl jdbcDeveloperRepositoryMock;

    @InjectMocks
    private DeveloperService developerServiceMock;

    @Test
    public void saveShouldReturnSameDeveloper(){
        Developer developerFromMock = new Developer();
        Mockito.when(jdbcDeveloperRepositoryMock.save(developerFromMock)).thenReturn(developerFromMock);
        Developer developer = developerServiceMock.save(developerFromMock);
        Assertions.assertEquals(developerFromMock, developer);
    }

    @Test
    public void updateShouldReturnSameDeveloper(){
        Developer developerFromMock = new Developer();
        Mockito.when(jdbcDeveloperRepositoryMock.update(developerFromMock)).thenReturn(developerFromMock);
        Developer developer = developerServiceMock.update(developerFromMock);
        Assertions.assertEquals(developer, developerFromMock);
    }

    @Test
    public void getAllShouldReturnListOfDevelopers(){
        List<Developer> developersFromMock = new ArrayList<>();
        Mockito.when(jdbcDeveloperRepositoryMock.getAll()).thenReturn(developersFromMock);
        List<Developer> developers = developerServiceMock.getAll();
        Assertions.assertEquals(developers, developersFromMock);
    }

    @Test
    public void getByIdShouldReturnDeveloperIfExistElseReturnNull(){
        Integer integer1 = 1;
        Integer integer2 = 2;
        Developer developerFromMock = new Developer();
        Mockito.when(jdbcDeveloperRepositoryMock.getById(integer1)).thenReturn(developerFromMock);
        Mockito.when(jdbcDeveloperRepositoryMock.getById(integer2)).thenReturn(null);
        Developer developer1 = developerServiceMock.getById(integer1);
        Developer developer2 = developerServiceMock.getById(integer2);
        Assertions.assertEquals(developer1, developerFromMock);
        Assertions.assertNull(developer2);
    }

    @Test
    public void deleteByIdShouldReturnTrueIfDeletedElseReturnFalse(){
        Integer integer1 = 1;
        Integer integer2 = 2;
        Mockito.when(jdbcDeveloperRepositoryMock.deleteById(integer1)).thenReturn(true);
        Mockito.when(jdbcDeveloperRepositoryMock.deleteById(integer2)).thenReturn(false);
        boolean b1 = developerServiceMock.deleteById(integer1);
        boolean b2 = developerServiceMock.deleteById(integer2);
        Assertions.assertTrue(b1);
        Assertions.assertFalse(b2);
    }

}
