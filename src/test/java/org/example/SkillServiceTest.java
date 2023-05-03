package org.example;

import org.example.model.Skill;
import org.example.repository.jdbc.JdbcSkillRepositoryImpl;
import org.example.service.SkillService;
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
public class SkillServiceTest {
    @Mock
    private JdbcSkillRepositoryImpl jdbcSkillRepositoryMock;

    @InjectMocks
    private SkillService skillServiceMock;

    @Test
    public void saveShouldReturnSameSkill(){
        Skill skillFromMock = new Skill();
        Mockito.when(jdbcSkillRepositoryMock.save(skillFromMock)).thenReturn(skillFromMock);
        Skill skill = skillServiceMock.save(skillFromMock);
        Assertions.assertEquals(skillFromMock, skill);
    }

    @Test
    public void updateShouldReturnSameSkill(){
        Skill skillFromMock = new Skill();
        Mockito.when(jdbcSkillRepositoryMock.update(skillFromMock)).thenReturn(skillFromMock);
        Skill skill = skillServiceMock.update(skillFromMock);
        Assertions.assertEquals(skill, skillFromMock);
    }

    @Test
    public void getAllShouldReturnListOfSkills(){
        List<Skill> skillsFromMock = new ArrayList<>();
        Mockito.when(jdbcSkillRepositoryMock.getAll()).thenReturn(skillsFromMock);
        List<Skill> skills = skillServiceMock.getAll();
        Assertions.assertEquals(skills, skillsFromMock);
    }

    @Test
    public void getByIdShouldReturnSkillIfExistElseReturnNull(){
        Integer integer1 = 1;
        Integer integer2 = 2;
        Skill skillFromMock = new Skill();
        Mockito.when(jdbcSkillRepositoryMock.getById(integer1)).thenReturn(skillFromMock);
        Mockito.when(jdbcSkillRepositoryMock.getById(integer2)).thenReturn(null);
        Skill skill1 = skillServiceMock.getById(integer1);
        Skill skill2 = skillServiceMock.getById(integer2);
        Assertions.assertEquals(skill1, skillFromMock);
        Assertions.assertNull(skill2);
    }

    @Test
    public void deleteByIdShouldReturnTrueIfDeletedElseReturnFalse(){
        Integer integer1 = 1;
        Integer integer2 = 2;
        Mockito.when(jdbcSkillRepositoryMock.deleteById(integer1)).thenReturn(true);
        Mockito.when(jdbcSkillRepositoryMock.deleteById(integer2)).thenReturn(false);
        boolean b1 = skillServiceMock.deleteById(integer1);
        boolean b2 = skillServiceMock.deleteById(integer2);
        Assertions.assertTrue(b1);
        Assertions.assertFalse(b2);
    }
}
