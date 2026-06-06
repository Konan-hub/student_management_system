package userinterface;

import model.Student;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleApp {

    private final Map<String, Student> studentDatabase = new HashMap<>();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        // Load existing profiles from disk right when the app starts
        loadDatabase();

        System.out.println("=== Welcome to the Student Management System ===");

        while (true) {
            try {

                System.out.println("\n--- MAIN MENU ---");
                System.out.println("1. Create an account (Register)");
                System.out.println("2. Access your account (Login)");
                System.out.println("3. Exit");
                System.out.println("4. Account Management (Update/Delete) ");
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

                        Student newStudent = new Student(name, age);
                        studentDatabase.put(newUsername, newStudent);

                        // Auto-save to disk right after a successful registration
                        saveDatabase();

                        System.out.println("\nProfile created and saved successfully!");
                        break;

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
                        System.out.println("Saving data before exit...");
                        // Final security save before closing the application
                        saveDatabase();
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;

                    case "4":
                        boolean inManagementMenu = true;
                        while (inManagementMenu) {
                            System.out.println("\n--- ACCOUNT MANAGEMENT ---");
                            System.out.println("1. Update your age");
                            System.out.println("2. Delete your account");
                            System.out.println("3. Back to Main Menu");
                            System.out.print("Select a management option: ");

                            String subChoice = scanner.nextLine().trim();

                            switch (subChoice) {
                                case "1": //  UPDATE MODE
                                    System.out.print("\nEnter your username to update your profile: ");
                                    String userToUpdate = scanner.nextLine().trim();

                                    if (studentDatabase.containsKey(userToUpdate)) {
                                        Student student = studentDatabase.get(userToUpdate);
                                        System.out.println("Current Profile -> Name: " + student.getName() + ", Age: " + student.getAge());

                                        System.out.print("Enter your new age: ");
                                        String newAgeInput = scanner.nextLine().trim();
                                        int newAge = Integer.parseInt(newAgeInput);

                                        student.setAge(newAge);

                                        saveDatabase();
                                        System.out.println("[Success] Age updated successfully!");
                                    } else {
                                        System.out.println("Error: Username not found.");
                                    }
                                    break;

                                case "2": // DELETE MODE
                                    System.out.print("\nEnter the username of the account to DELETE: ");
                                    String userToDelete = scanner.nextLine().trim();

                                    if (studentDatabase.containsKey(userToDelete)) {
                                        System.out.print("Are you sure? This action is irreversible! (yes/no): ");
                                        String confirmation = scanner.nextLine().trim().toLowerCase();

                                        if (confirmation.equals("yes")) {
                                            Student deletedStudent = studentDatabase.remove(userToDelete);
                                            saveDatabase();
                                            System.out.println("[Success] Account '" + userToDelete + "' (Real Name: " + deletedStudent.getName() + ") has been deleted.");
                                        } else {
                                            System.out.println("Deletion canceled.");
                                        }
                                    } else {
                                        System.out.println("Error: Username not found.");
                                    }
                                    break;

                                case "3": //  RETURN MODE
                                    System.out.println("Returning to Main Menu...");
                                    inManagementMenu = false;
                                    break;

                                default:
                                    System.out.println("Invalid option! Please enter 1, 2, 3 or 4.");
                                    break;
                            }
                        }
                        break;


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

    // Getter to allow our test class to verify the database state
    public Map<String, Student> getStudentDatabase() {
        return this.studentDatabase;
    }

    // Serializes the map and writes it to a binary file
    private void saveDatabase() {
        // FileOutputStream targets the file, ObjectOutputStream serializes the objects
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.ser"))) {
            oos.writeObject(studentDatabase); // Writes the entire map ecosystem in one go
            System.out.println("System Database saved successfully to disk");
        } catch (IOException e) {
            System.out.println(" System Error : Failed to save database : " + e.getMessage());
        }
    }

    // Reads the binary file and deserializes it back into the active Map
    @SuppressWarnings("unchecked")
    private void loadDatabase() {
        java.io.File file = new java.io.File("students.ser");

        // If the app runs for the very first time, prevent crashing
        if (!file.exists()) {
            System.out.println("[System] No existing database found. Starting fresh.");
            return;
        }

        // FileInputStream reads raw bytes, ObjectInputStream reconstructs Java objects
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Map<String, Student> loadedData = (Map<String, Student>) ois.readObject();
            studentDatabase.clear(); // Wipe runtime data to prevent duplicates
            studentDatabase.putAll(loadedData); // Restore saved database into memory
            System.out.println("[System] Database loaded successfully. Profiles restored: " + studentDatabase.size());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[System Error] Failed to load database: " + e.getMessage());
        }
    }
}