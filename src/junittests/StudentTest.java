package junittests;

import model.Student;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {


    // 1. NAME VALIDATION TESTS
    @Test
    public void shouldCreateStudentWithValidData() {
        Student student = new Student("Max", 22);
        assertEquals("Max", student.getName());
        assertEquals(22, student.getAge());
    }

    @Test
    public void shouldThrowExceptionWhenNameContainsNumbers() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Max123", 20);
        });
    }

    @Test
    public void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("   ", 20);
        });
    }

// 2. AGE VALIDATION TESTS
    @Test
    public void shouldThrowExceptionWhenAgeIsExactlyZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Anna", 0);
        });
    }

    @Test
    public void shouldThrowExceptionWhenAgeIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Anna", -5);
        });
    }

    @Test
    public void shouldThrowExceptionWhenAgeIsGreaterThan130() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Anna", 131);
        });
    }
}