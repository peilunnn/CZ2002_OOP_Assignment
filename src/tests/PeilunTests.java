import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Peilun;

public class PeilunTests {
    @Test
    void addTwoNumbers_fiveAndTen_ShouldReturnFifteen() {
        assertEquals(addTwoNumbers(5, 10), 15);
    }
}