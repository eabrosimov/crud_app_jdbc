package org.example.controller;

import org.example.model.Developer;
import org.example.service.DeveloperService;

import java.util.List;

public class DeveloperController {
    private final DeveloperService developerService = new DeveloperService();

    public Developer save(String firstName, String lastName){
        String clearedFirstName = firstName.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
        String clearedLastName = lastName.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
        if(clearedFirstName.isEmpty() || clearedLastName.isEmpty())
            return null;
        if(clearedFirstName.length() > 25 || clearedLastName.length() > 25)
            return null;

        Developer developer = new Developer();
        developer.setFirstName(clearedFirstName);
        developer.setLastName(clearedLastName);

        return developerService.save(developer);
    }

    public Developer getById(Integer id){
        if (id <= 0)
            return null;

        return developerService.getById(id);
    }

    public List<Developer> getAll(){
        return developerService.getAll();
    }

    public Developer update(Developer developer) {
        Developer outdatedDeveloper = getById(developer.getId());

        if (!developer.getFirstName().equals(outdatedDeveloper.getFirstName())) {
            String clearedFirstName = developer.getFirstName().replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
            developer.setFirstName(clearedFirstName);
            if (developer.getFirstName().isEmpty())
                return null;
            if (developer.getFirstName().length() > 25)
                return null;
        }

        if (!developer.getLastName().equals(outdatedDeveloper.getLastName())) {
            String clearedLastName = developer.getLastName().replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
            developer.setLastName(clearedLastName);
            if (developer.getLastName().isEmpty())
                return null;
            if (developer.getLastName().length() > 25)
                return null;
        }

        return developerService.update(developer);
    }

    public boolean deleteById(Integer id){
        if(id <= 0)
            return false;

        return developerService.deleteById(id);
    }
}