package model;

public class Student {
    private String name ;
    private int age ;

    public Student(String name, int age) {
        setName(name);
        setAge(age);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
            return age;
        }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is invalid!");
        }
        if ( !name.matches("\\p{L}+")) {
            throw new IllegalArgumentException("Name must contain only letters!");
        }
        this.name = name;

            }

    public void setAge(int age) {
        if ( age <= 0 || age > 130 ) {
            throw new IllegalArgumentException("Age must be between 1 and 130 ");

        }
        this.age = age;
    }
}

