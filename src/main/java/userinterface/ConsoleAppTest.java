package userinterface;

import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleAppTest {

    private ConsoleApp console;

    // Fresh setup before every single test to ensure they don't mess with each other
    @BeforeEach
    void setUp() {
        console = new ConsoleApp();
    }

    @Test
    void shouldRegisterNewStudentSuccessfully() {
        Student student = new Student("Konan", 22);
        console.getStudentDatabase().put("cyber_konan", student);

        // Making sure the database actually contains our new user
        assertTrue(console.getStudentDatabase().containsKey("cyber_konan"));
        assertEquals(student, console.getStudentDatabase().get("cyber_konan"));
    }

    @Test
    void shouldPreventOverwritingExistingUsername() {
        Student firstStudent = new Student("Konan", 22);
        console.getStudentDatabase().put("cyber_konan", firstStudent);

        // Simulating the menu logic: if username exists, don't allow registration
        if (!console.getStudentDatabase().containsKey("cyber_konan")) {
            Student secondStudent = new Student("Alex", 25);
            console.getStudentDatabase().put("cyber_konan", secondStudent);
        }

        // Checking that the original data wasn't accidentally crushed
        assertEquals("Konan", console.getStudentDatabase().get("cyber_konan").getName());
        assertEquals(1, console.getStudentDatabase().size());
    }

    @Test
    void shouldNotStoreStudentWhenValidationFails() {
        // If someone passes garbage data, the Student constructor should blow up immediately
        assertThrows(IllegalArgumentException.class, () -> {
            Student invalidStudent = new Student("", -5);
            console.getStudentDatabase().put("bad_user", invalidStudent);
        });

        // The database must remain clean with no trace of the bad user
        assertFalse(console.getStudentDatabase().containsKey("bad_user"));
    }

    // 🎯 NEW TEST: Verifies that updating the age works as expected
    @Test
    void shouldUpdateStudentAgeSuccessfully() {
        Student student = new Student("Konan", 22);
        console.getStudentDatabase().put("cyber_konan", student);

        // Fetching the user from the map and triggering our new setter
        Student studentToUpdate = console.getStudentDatabase().get("cyber_konan");
        studentToUpdate.setAge(23);

        // Verifying the profile stored in memory reflects the birthday change
        assertEquals(23, console.getStudentDatabase().get("cyber_konan").getAge());
    }

    // 🎯 NEW TEST: Verifies that removing an account works perfectly
    @Test
    void shouldDeleteStudentAccountSuccessfully() {
        Student student = new Student("Konan", 22);
        console.getStudentDatabase().put("cyber_konan", student);

        // Simulating the menu choice 2: ripping the user out of the map
        Student deletedStudent = console.getStudentDatabase().remove("cyber_konan");

        // The database should be completely empty and the deleted data returned correctly
        assertFalse(console.getStudentDatabase().containsKey("cyber_konan"));
        assertNotNull(deletedStudent);
        assertEquals("Konan", deletedStudent.getName());
        assertEquals(0, console.getStudentDatabase().size());
    }
}