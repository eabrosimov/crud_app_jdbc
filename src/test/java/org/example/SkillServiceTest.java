package org.example;

import org.example.model.Skill;
import org.example.repository.jdbc.JdbcSkillRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {
    @Mock
    JdbcSkillRepositoryImpl jdbcSkillRepositoryMock;

    @Test
    public void save(){
        Skill skillFromMock = new Skill();
        Mockito.when(jdbcSkillRepositoryMock.save(skillFromMock)).thenReturn(skillFromMock);
        Skill skill = jdbcSkillRepositoryMock.save(skillFromMock);
        Assertions.assertEquals(skillFromMock, skill);
    }

    @Test
    public void update(){
        Skill skillFromMock = new Skill();
        Mockito.when(jdbcSkillRepositoryMock.update(skillFromMock)).thenReturn(skillFromMock);
        Skill skill = jdbcSkillRepositoryMock.update(skillFromMock);
        Assertions.assertEquals(skill, skillFromMock);
    }

    @Test
    public void getAllTest(){
        List<Skill> skillsFromMock = new ArrayList<>();
        Mockito.when(jdbcSkillRepositoryMock.getAll()).thenReturn(skillsFromMock);
        List<Skill> skills = jdbcSkillRepositoryMock.getAll();
        Assertions.assertEquals(skills, skillsFromMock);
    }

    @Test
    public void getById(){
        Integer integer1 = 1;
        Integer integer2 = 2;
        Skill skillFromMock = new Skill();
        Mockito.when(jdbcSkillRepositoryMock.getById(integer1)).thenReturn(skillFromMock);
        Mockito.when(jdbcSkillRepositoryMock.getById(integer2)).thenReturn(null);
        Skill skill1 = jdbcSkillRepositoryMock.getById(integer1);
        Skill skill2 = jdbcSkillRepositoryMock.getById(integer2);
        Assertions.assertEquals(skill1, skillFromMock);
        Assertions.assertNull(skill2);
    }

    @Test
    public void deleteById(){
        Integer integer1 = 1;
        Integer integer2 = 2;
        Mockito.when(jdbcSkillRepositoryMock.deleteById(integer1)).thenReturn(true);
        Mockito.when(jdbcSkillRepositoryMock.deleteById(integer2)).thenReturn(false);
        boolean b1 = jdbcSkillRepositoryMock.deleteById(integer1);
        boolean b2 = jdbcSkillRepositoryMock.deleteById(integer2);
        Assertions.assertTrue(b1);
        Assertions.assertFalse(b2);
    }
}
