package org.example;

import org.example.model.Specialty;
import org.example.repository.jdbc.JdbcSpecialtyRepositoryImpl;
import org.example.service.SpecialtyService;
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
public class SpecialtyServiceTest {
    @Mock
    JdbcSpecialtyRepositoryImpl jdbcSpecialtyRepositoryMock;

    @InjectMocks
    SpecialtyService specialtyServiceMock;

    @Test
    public void saveShouldReturnSameSpecialty(){
        Specialty specialtyFromMock = new Specialty();
        Mockito.when(jdbcSpecialtyRepositoryMock.save(specialtyFromMock)).thenReturn(specialtyFromMock);
        Specialty specialty = specialtyServiceMock.save(specialtyFromMock);
        Assertions.assertEquals(specialtyFromMock, specialty);
    }

    @Test
    public void updateShouldReturnSameSpecialty(){
        Specialty specialtyFromMock = new Specialty();
        Mockito.when(jdbcSpecialtyRepositoryMock.update(specialtyFromMock)).thenReturn(specialtyFromMock);
        Specialty specialty = specialtyServiceMock.update(specialtyFromMock);
        Assertions.assertEquals(specialty, specialtyFromMock);
    }

    @Test
    public void getAllShouldReturnListOfSpecialties(){
        List<Specialty> specialtiesFromMock = new ArrayList<>();
        Mockito.when(jdbcSpecialtyRepositoryMock.getAll()).thenReturn(specialtiesFromMock);
        List<Specialty> specialties = specialtyServiceMock.getAll();
        Assertions.assertEquals(specialties, specialtiesFromMock);
    }

    @Test
    public void getByIdShouldReturnSpecialtyIfExistElseReturnNull(){
        Integer integer1 = 1;
        Integer integer2 = 2;
        Specialty specialtyFromMock = new Specialty();
        Mockito.when(jdbcSpecialtyRepositoryMock.getById(integer1)).thenReturn(specialtyFromMock);
        Mockito.when(jdbcSpecialtyRepositoryMock.getById(integer2)).thenReturn(null);
        Specialty specialty1 = specialtyServiceMock.getById(integer1);
        Specialty specialty2 = specialtyServiceMock.getById(integer2);
        Assertions.assertEquals(specialty1, specialtyFromMock);
        Assertions.assertNull(specialty2);
    }

    @Test
    public void deleteByIdShouldReturnTrueIfDeletedElseReturnFalse(){
        Integer integer1 = 1;
        Integer integer2 = 2;
        Mockito.when(jdbcSpecialtyRepositoryMock.deleteById(integer1)).thenReturn(true);
        Mockito.when(jdbcSpecialtyRepositoryMock.deleteById(integer2)).thenReturn(false);
        boolean b1 = specialtyServiceMock.deleteById(integer1);
        boolean b2 = specialtyServiceMock.deleteById(integer2);
        Assertions.assertTrue(b1);
        Assertions.assertFalse(b2);
    }

}
