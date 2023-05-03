package org.example.service;

import org.example.model.Developer;
import org.example.repository.DeveloperRepository;
import org.example.repository.jdbc.JdbcDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public DeveloperService(){
        developerRepository = new JdbcDeveloperRepositoryImpl();
    }

    public DeveloperService(DeveloperRepository developerRepository){
        this.developerRepository = developerRepository;
    }

    public Developer save(Developer developer){
        return developerRepository.save(developer);
    }

    public Developer update(Developer developer){
        return developerRepository.update(developer);
    }

    public List<Developer> getAll(){
        return developerRepository.getAll();
    }

    public Developer getById(Integer integer){
        return developerRepository.getById(integer);
    }

    public boolean deleteById(Integer integer){
        return developerRepository.deleteById(integer);
    }
}
