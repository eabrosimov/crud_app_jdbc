package org.example.controller;

import org.example.model.Skill;
import org.example.service.SkillService;

import java.util.List;

public class SkillController {
    private final SkillService skillService = new SkillService();

    public Skill save(String name) {
        String clearedName = name.replaceAll("[^a-zA-Zа-яёА-ЯЁ ]", "");

        if(clearedName.isEmpty() || clearedName.isBlank()) {
            return null;
        }
        if(clearedName.length() > 25) {
            return null;
        }

        Skill skill = new Skill();
        skill.setName(clearedName);

        return skillService.save(skill);
    }

    public Skill getById(Integer id) {
        if(id <= 0) {
            return null;
        }

        return skillService.getById(id);
    }

    public List<Skill> getAll() {
        return skillService.getAll();
    }

    public Skill update(Skill skill) {
        skill.setName(skill.getName().replaceAll("[^a-zA-Zа-яёА-ЯЁ ]", ""));

        if(skill.getName().isEmpty() || skill.getName().isBlank()) {
            return null;
        }
        if(skill.getName().length() > 25) {
            return null;
        }

        return skillService.update(skill);
    }

    public boolean deleteById(Integer id){
        if(id <= 0) {
            return false;
        }

        return skillService.deleteById(id);
    }
}