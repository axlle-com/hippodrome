import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {

    @Test
    public void constructor() {
        IllegalArgumentException exception0 = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception0.getMessage());

        List<Horse> list = new ArrayList<>();
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(list));
        assertEquals("Horses cannot be empty.", exception1.getMessage());
    }

    @Test
    public void getHorses() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("Name" + i, i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horseList);

        assertEquals(horseList, hippodrome.getHorses());
    }

    @Test
    public void move() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horseList.add(mock(Horse.class));
        }

        new Hippodrome(horseList).move();

        for (Horse horse : horseList) {
            verify(horse).move();
        }
    }

    @Test
    public void getWinner() {
        Horse horse0 = new Horse("name", 1, 1);
        Horse horse1 = new Horse("name", 1, 1);
        Horse horse2 = new Horse("name", 1, 5);
        Horse horse3 = new Horse("name", 1, 1);
        Horse horse4 = new Horse("name", 1, 1);

        Hippodrome hippodrome = new Hippodrome(List.of(horse0, horse1, horse2, horse3, horse4));

        assertSame(horse2, hippodrome.getWinner());
    }
}
