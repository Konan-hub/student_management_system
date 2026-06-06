package userinterface;

import model.Student;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleApp {

    private final Map<String, Student> studentDatabase = new HashMap<>();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Welcome to the Student Management System ===");

        while (true) {
            try {

                System.out.println("\n--- MAIN MENU ---");
                System.out.println("1. Create an account (Register)");
                System.out.println("2. Access your account (Login)");
                System.out.println("3. Exit");
                System.out.print("Select an option: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        System.out.println("\n[Registration Mode]");
                        System.out.print("Choose a unique username: ");
                        String newUsername = scanner.nextLine().trim();

                        if (newUsername.isEmpty()) {
                            System.out.println("Error: Username cannot be empty.");
                            break;
                        }

                        if (studentDatabase.containsKey(newUsername)) {
                            System.out.println("Error: This username is already taken! Try another one.");
                            break;
                        }

                        System.out.print("Enter your real name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter your age: ");
                        String ageInput = scanner.nextLine();
                        int age = Integer.parseInt(ageInput);

                        // Création et sauvegarde
                        Student newStudent = new Student(name, age);
                        studentDatabase.put(newUsername, newStudent);

                        System.out.println("\nProfile created and saved successfully!");
                        break; // Fin du CAS 1

                    case "2":
                        System.out.println("\n[Login Mode]");
                        System.out.print("Enter your username: ");
                        String loginUsername = scanner.nextLine().trim();

                        if (studentDatabase.containsKey(loginUsername)) {
                            Student existingStudent = studentDatabase.get(loginUsername);
                            System.out.println("\n[Welcome back!] Profile found:");
                            System.out.println("Name: " + existingStudent.getName());
                            System.out.println("Age: " + existingStudent.getAge());
                        } else {
                            System.out.println("Error: Username not found. Please register first.");
                        }
                        break;

                    case "3":
                    case "exit":
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid option! Please enter 1, 2, or 3.");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Age must be a number! Returning to menu.\n");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + " Returning to menu.\n");
            }
        }
    }
}