package org.example.service;

import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.example.repository.jdbc.JdbcSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository = new JdbcSpecialtyRepositoryImpl();

    public Specialty save(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public Specialty update(Specialty specialty) {
        return specialtyRepository.update(specialty);
    }

    public List<Specialty> getAll() {
        return specialtyRepository.getAll();
    }

    public Specialty getById(Integer integer) {
        return specialtyRepository.getById(integer);
    }

    public boolean deleteById(Integer integer) {
        return specialtyRepository.deleteById(integer);
    }
}
