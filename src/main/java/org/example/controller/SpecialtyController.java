package org.example.controller;

import org.example.model.Specialty;
import org.example.service.SpecialtyService;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyService specialtyService = new SpecialtyService();

    public Specialty save(String name) {
        String clearedName = name.replaceAll("[^a-zA-Zа-яёА-ЯЁ ]", "");

        if(clearedName.isEmpty() || clearedName.isBlank()) {
            return null;
        }
        if(clearedName.length() > 25) {
            return null;
        }

        Specialty specialty = new Specialty();
        specialty.setName(clearedName);

        return specialtyService.save(specialty);
    }

    public Specialty getById(Integer id) {
        if(id <= 0) {
            return null;
        }

        return specialtyService.getById(id);
    }

    public List<Specialty> getAll() {
        return specialtyService.getAll();
    }

    public Specialty update(Specialty specialty) {
        specialty.setName(specialty.getName().replaceAll("[^a-zA-Zа-яёА-ЯЁ ]", ""));

        if(specialty.getName().isEmpty() || specialty.getName().isBlank()) {
            return null;
        }
        if(specialty.getName().length() > 25) {
            return null;
        }

        return specialtyService.update(specialty);
    }

    public boolean deleteById(Integer id){
        if(id <= 0) {
            return false;
        }

        return specialtyService.deleteById(id);
    }
}