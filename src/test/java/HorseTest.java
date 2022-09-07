import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test
    public void nameException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    public void nameExceptionMessage() {
        try {
            new Horse(null, 1, 1);
        } catch (IllegalArgumentException exception) {
            assertEquals("Name cannot be null.", exception.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t\t\t"})
    public void nameExceptionParameterized(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1})
    public void speedExceptionParameterized(double speed) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("name", speed, 1));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1})
    public void distanceExceptionParameterized(double distance) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("name", 1, distance));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void getName() {
        Horse horse = new Horse("name", 1, 1);
        assertEquals("name", horse.getName());
    }

    @Test
    public void getSpeed() {
        Horse horse = new Horse("name", 1, 1);
        assertEquals(1, horse.getSpeed());
    }

    @Test
    public void getDistance() {
        Horse horse0 = new Horse("name", 1, 1);
        assertEquals(1, horse0.getDistance());
        Horse horse1 = new Horse("name", 1);
        assertEquals(0, horse1.getDistance());
    }

    @Test
    public void move() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("name", 1, 1).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.4})
    public void getRandomDouble(double doubles) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("name", 31, 283);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(doubles);

            horse.move();

            assertEquals(283 + 31 * doubles, horse.getDistance());
        }
    }
}
