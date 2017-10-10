import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Learner
 *
 * @author Arshan Hashemi
 * @version 5/10/17
 */
public class SlidingWindowTest {
    @org.junit.Test
    public void getWindow_test() throws Exception {

        SlidingWindow window = new SlidingWindow(5, 2);

        double[][] reading1 = {{0, 0, 0}, {0, 0, 0}};
        double[][] reading2 = {{10, 10, 10}, {10, 10, 10}};
        double[][] reading3 = {{20, 20, 20}, {20, 20, 20}};
        double[][] reading4 = {{30, 30, 30}, {30, 30, 30}};
        double[][] reading5 = {{40, 40, 40}, {40, 40, 40}};

        window.insert(reading1);
        window.insert(reading2);
        window.insert(reading3);
        window.insert(reading4);
        window.insert(reading5);

        window.insert(reading3);
        /*
        assertEquals(window.getSize(), 5);
        System.out.println(Arrays.toString(window.getSlidingAvg()[0]));
        System.out.println(Arrays.toString(window.getSlidingAvg()[1]));
        assertTrue(Arrays.equals(window.getSlidingAvg()[0], new double[]{24, 24}));
        assertTrue(Arrays.equals(window.getSlidingAvg()[1], new double[]{24, 24}));

        window.insert(reading3);

        assertEquals(window.getSize(), 5);
        assertTrue(Arrays.equals(window.getSlidingAvg()[0], new double[]{26, 26}));
        assertTrue(Arrays.equals(window.getSlidingAvg()[1], new double[]{26, 26}));
        */
    }
}