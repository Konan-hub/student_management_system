package userinterface;

import model.Student;
import java.util.Scanner;

public class ConsoleApp {

    public void start() {
        Scanner scanner = new Scanner(System.in); // On ouvre le scanner ICI (une seule fois)

        while (true) {
            try {

                System.out.print("Enter your name (or 'exit' to quit): ");
                String name = scanner.nextLine();

                if (name.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye");
                    break;
                }

                System.out.print("Enter your age: ");
                String ageInput = scanner.nextLine();

                int age = Integer.parseInt(ageInput);

                Student student = new Student(name, age);

                System.out.println("\nStudent created successfully!");
                System.out.println("Name: " + student.getName());
                System.out.println("Age: " + student.getAge() + "\n");

            } catch (NumberFormatException e) {
                System.out.println("Error: Age must be a number! Try again.\n");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + " Try again.\n");
            }
        }

        scanner.close();
    }
}