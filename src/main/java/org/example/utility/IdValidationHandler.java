package org.example.utility;

import java.util.Scanner;

public class IdValidationHandler {
    private static final Scanner reader = new Scanner(System.in);
    public static int validateId(){
        int id;
        while (true) {
            System.out.print("Введите id: ");
            if (reader.hasNextInt()) {
                id = reader.nextInt();
                break;
            } else {
                System.out.println("id не может содержать буквы или символы");
                reader.nextLine();
            }
        }
        reader.nextLine();

        return id;
    }
}
