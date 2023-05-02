package org.example.view;

import org.example.controller.DeveloperController;
import org.example.controller.SkillController;
import org.example.controller.SpecialtyController;
import org.example.model.Developer;
import org.example.model.Skill;
import org.example.utility.IdValidationHandler;
import org.example.model.Specialty;

import java.util.Scanner;

public class DeveloperView {
    private final DeveloperController developerController = new DeveloperController();
    private final SkillController skillController = new SkillController();
    private final SpecialtyController specialtyController = new SpecialtyController();
    private final Scanner reader = new Scanner(System.in);

    public void handleRequest(){
        showListOfCommands();

        String command;

        while(true){
            command = reader.nextLine();
            switch (command){
                case "-c":
                    createDeveloper();
                    break;
                case "-r":
                    getDeveloperById();
                    break;
                case "-ra":
                    getAllDevelopers();
                    break;
                case "-u":
                    updateDeveloper();
                    break;
                case "-d":
                    deleteDeveloper();
                    break;
                case "-help":
                    showListOfCommands();
                    break;
                case "-exit":
                    System.out.println("Выход в главное меню");
                    return;
                default:
                    System.out.println("Вы ввели неверную команду\n" +
                            "список команд: -help");
            }
        }
    }

    private void showListOfCommands(){
        System.out.printf("\n***Вы в редакторе таблицы developer***\n\n" +
                "Список команд:\n" +
                "%-15s -c\n" +
                "%-15s -r\n" +
                "%-15s -ra\n" +
                "%-15s -u\n" +
                "%-15s -d\n" +
                "%-15s -help\n" +
                "%-15s -exit\n", "добавить:", "получить:", "получить всех:", "изменить:", "удалить:", "список команд:", "главное меню:");
    }

    private void createDeveloper(){
        System.out.print("Введите имя: ");
        String firstName = reader.nextLine();

        System.out.print("Введите фамилию: ");
        String lastName = reader.nextLine();

        Developer developer = developerController.save(firstName, lastName);
        if(developer == null)
            System.out.println("Неверный формат\n" +
                    "Убедитесь, что строки не пустые и не превышают 25 символов");
        else
            System.out.printf("Создан новый разработчик: %s\n", developer);
    }

    public void getDeveloperById(){
        int id = IdValidationHandler.validateId();
        Developer developer = developerController.getById(id);
        if(developer == null)
            System.out.println("Разработчик с таким id не существует");
        else
            System.out.printf("%s\n", developer);
    }

    public void getAllDevelopers(){
        for(Developer d: developerController.getAll())
            System.out.println(d);
    }

    public void updateDeveloper(){
        int id = IdValidationHandler.validateId();
        Developer developer = developerController.getById(id);
        if(developer == null)
            System.out.println("Разработчик с таким id не существует");
        else{
            System.out.println("Введите нужное поле для редактирования:\n" +
                    ">> first name <<\n" +
                    ">> last name <<\n" +
                    ">> skill <<\n" +
                    ">> specialty <<");

            String fieldName = reader.nextLine();
            switch (fieldName){
                case "first name":
                    System.out.print("Введите имя: ");
                    String firstName = reader.nextLine();

                    developer.setFirstName(firstName);
                    if(developerController.update(developer) == null)
                        System.out.println("Неверный формат\n" +
                                "Убедитесь, что строка не пустая и не превышает 25 символов");
                    else
                        System.out.printf("Имя разработчика изменено: %s\n", developer);
                    break;

                case "last name":
                    System.out.print("Введите фамилию: ");
                    String lastName = reader.nextLine();

                    developer.setLastName(lastName);
                    if(developerController.update(developer) == null)
                        System.out.println("Неверный формат\n" +
                                "Убедитесь, что строка не пустая и не превышает 25 символов");
                    else
                        System.out.printf("Фамилия разработчика изменена: %s\n", developer);
                    break;

                case "skill":
                    int skillId = IdValidationHandler.validateId();
                    Skill skill = skillController.getById(skillId);
                    if(skill == null)
                        System.out.println("Скилл с таким id не существует");
                    else{
                        System.out.println("Выберете действие:\n" +
                                "добавить скилл -a\n" +
                                "удалить скилл  -d");
                        String action = reader.nextLine();
                        if(action.equals("-a")){
                            if(developer.getSkills().contains(skill)){
                                System.out.println("У разработчика уже есть такой скил");
                            } else {
                                developer.setSkills(skill);
                                developerController.update(developer);
                                System.out.printf("Добавлен новый скил: %s\n", developer);
                            }
                        } else if(action.equals("-d")){
                            if(developer.getSkills().remove(skill)){
                                developerController.update(developer);
                                System.out.printf("Скил удален: %s\n", developer);
                            } else{
                                System.out.println("У разработчика нет такого скила");
                            }
                        } else{
                            System.out.println("Неверная команда");
                        }
                    }
                    break;

                case "specialty":
                    int specialtyId = IdValidationHandler.validateId();
                    Specialty specialty = specialtyController.getById(specialtyId);
                    if(specialty == null)
                        System.out.println("Специальность с таким id не существует");
                    else{
                        developer.setSpecialty(specialty);
                        developerController.update(developer);
                        System.out.printf("Специальность разработчика изменена: %s\n", developer);
                    }
                    break;

                default:
                    System.out.println("Такое поле не существует");
            }
        }
    }

    public void deleteDeveloper(){
        int id = IdValidationHandler.validateId();
        if(developerController.deleteById(id))
            System.out.printf("Разработчик с id %d удалён\n", id);
        else
            System.out.println("Разработчик с таким id не существует");
    }
}
