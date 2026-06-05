package userinterface;
import model.Student;
import java.util.Scanner;

public class ConsoleApp {

        public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            System.out.print("Enter your age: ");
            String ageInput = scanner.nextLine();

            try {
                int age = Integer.parseInt(ageInput);

                Student student = new Student(name, age);

                System.out.println("\nStudent created successfully!");
                System.out.println("Name: " + student.getName());
                System.out.println("Age: " + student.getAge());

            } catch (NumberFormatException e) {
                System.out.println("Error: Age must be a number!");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

            scanner.close();
        }
    }

