package org.example.view;

import org.example.controller.SpecialtyController;
import org.example.model.Specialty;
import org.example.utility.IdValidationHandler;

import java.util.Scanner;

public class SpecialtyView {
    private final SpecialtyController specialtyController = new SpecialtyController();
    private final Scanner reader = new Scanner(System.in);

    public void handleRequest() {
        showListOfCommands();

        String command;

        while(true){
            command = reader.nextLine();
            switch (command){
                case "-c":
                    createSpecialty();
                    break;
                case "-r":
                    getSpecialtyById();
                    break;
                case "-ra":
                    getAllSpecialties();
                    break;
                case "-u":
                    updateSpecialty();
                    break;
                case "-d":
                    deleteSpecialty();
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

    private void showListOfCommands() {
        System.out.printf("\n***Вы в редакторе таблицы specialty***\n\n" +
                "Список команд:\n" +
                "%-15s -c\n" +
                "%-15s -r\n" +
                "%-15s -ra\n" +
                "%-15s -u\n" +
                "%-15s -d\n" +
                "%-15s -help\n" +
                "%-15s -exit\n", "добавить:", "получить:", "получить всех:", "изменить:", "удалить:", "список команд:", "главное меню:");
    }

    public void createSpecialty() {
        System.out.print("Введите название: ");
        String name = reader.nextLine();
        Specialty specialty = specialtyController.save(name);

        if(specialty == null) {
            System.out.println("Не удалось создать специальность\n" +
                    "Убедитесь, что:\n" +
                    "- строка не пустая и не превышает 25 символов\n" +
                    "- специальность не была добавлена ранее");
        } else {
            System.out.printf("Создана новая специальность: %s\n", specialty);
        }
    }

    public void getSpecialtyById() {
        int id = IdValidationHandler.validateId();
        Specialty specialty = specialtyController.getById(id);

        if(specialty == null) {
            System.out.println("Специальность с таким id не существует");
        } else {
            System.out.printf("%s\n", specialty);
        }

    }

    public void getAllSpecialties() {
        for(Specialty s: specialtyController.getAll()) {
            System.out.println(s);
        }
    }

    public void updateSpecialty() {
        int id = IdValidationHandler.validateId();
        Specialty specialty = specialtyController.getById(id);

        if(specialty == null) {
            System.out.println("Специальность с таким id не существует");
        } else {
            System.out.print("Введите новое название: ");
            String name = reader.nextLine();
            specialty.setName(name);

            if(specialtyController.update(specialty) == null) {
                System.out.println("Не удалось изменить специальность\n" +
                                    "Убедитесь, что:\n" +
                                    "- строка не пустая и не превышает 25 символов\n" +
                                    "- изменённое название не совпадает с уже существующей специальностью");
            } else {
                System.out.printf("Специальность изменена: %s\n", specialty);
            }
        }
    }

    public void deleteSpecialty() {
        int id = IdValidationHandler.validateId();

        if(specialtyController.deleteById(id)) {
            System.out.printf("Специальность с id %d удалена\n", id);
        } else {
            System.out.println("Не удалось удалить специальность\n" +
                    "Убедитесь, что:\n" +
                    "- специальность с таким id существует\n" +
                    "- специальность не связана с другими сущностями\n");
        }
    }
}