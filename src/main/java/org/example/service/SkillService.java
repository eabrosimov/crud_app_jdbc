package org.example.service;

import org.example.model.Skill;
import org.example.repository.SkillRepository;
import org.example.repository.jdbc.JdbcSkillRepositoryImpl;

import java.util.List;

public class SkillService {
    private final SkillRepository skillRepository = new JdbcSkillRepositoryImpl();

    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill update(Skill skill) {
        return skillRepository.update(skill);
    }

    public List<Skill> getAll() {
        return skillRepository.getAll();
    }

    public Skill getById(Integer integer) {
        return skillRepository.getById(integer);
    }

    public boolean deleteById(Integer integer) {
        return skillRepository.deleteById(integer);
    }
}
