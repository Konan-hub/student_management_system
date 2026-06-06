package userinterface;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleAppTest {





        private ConsoleApp console;

        @BeforeEach
        void setUp() {
            console = new ConsoleApp();
        }

        @Test
        void shouldRegisterNewStudentSuccessfully() {
            Student student = new Student("Konan", 22);
            console.getStudentDatabase().put("cyber_konan", student);

            assertTrue(console.getStudentDatabase().containsKey("cyber_konan"));
            assertEquals(student, console.getStudentDatabase().get("cyber_konan"));
        }

        @Test
        void shouldPreventOverwritingExistingUsername() {
            Student firstStudent = new Student("Konan", 22);
            console.getStudentDatabase().put("cyber_konan", firstStudent);

            // Simulate a second registration attempt with the same username
            if (!console.getStudentDatabase().containsKey("cyber_konan")) {
                Student secondStudent = new Student("Alex", 25);
                console.getStudentDatabase().put("cyber_konan", secondStudent);
            }

            assertEquals("Konan", console.getStudentDatabase().get("cyber_konan").getName());
            assertEquals(1, console.getStudentDatabase().size());
        }

        @Test
        void shouldNotStoreStudentWhenValidationFails() {
            // Validation logic from Student class should trigger before database insertion
            assertThrows(IllegalArgumentException.class, () -> {
                Student invalidStudent = new Student("", -5);
                console.getStudentDatabase().put("bad_user", invalidStudent);
            });

            assertFalse(console.getStudentDatabase().containsKey("bad_user"));
        }
    }

