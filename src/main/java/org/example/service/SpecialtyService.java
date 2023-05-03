package org.example.service;

import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.example.repository.jdbc.JdbcSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService(){
        specialtyRepository = new JdbcSpecialtyRepositoryImpl();
    }

    public SpecialtyService(SpecialtyRepository specialtyRepository){
        this.specialtyRepository = specialtyRepository;
    }

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
