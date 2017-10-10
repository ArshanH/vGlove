import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Learner
 *
 * @author Arshan Hashemi
 * @version 5/16/17
 */
public class Learner {

    public static void main(String[] args) throws IOException, Exception {

        Gesture middleFing = Learner.createGesture("5-11TestData/michaelmiddlefinger.bin");

        System.out.println();
    }

    public static Gesture createGesture(String fileName) throws IOException, Exception {
        DataParser parser = new DataParser();
        byte holder[] = new byte[DataParser.ACCEL_TOTAL * 4];
        ArrayList<double[][]> readings = new ArrayList<>(10);
        int i;

        File binaryFile = new File(fileName);
        FileInputStream stream = new FileInputStream(binaryFile);


        while (stream.read(holder) >= 0) {
            for (i = 0; i < DataParser.ACCEL_TOTAL; i++)
                parser.setAccels(i, holder);

            readings.add(parser.getAccels());
        }

        return new Gesture(readings);
    }

        public static Gesture createGesture(String name, String fileName) throws IOException, Exception {
        DataParser parser = new DataParser();
        byte holder[] = new byte[DataParser.ACCEL_TOTAL * 4];
        ArrayList<double[][]> readings = new ArrayList<>(10);
        int i;

        File binaryFile = new File(fileName);
        FileInputStream stream = new FileInputStream(binaryFile);


        while (stream.read(holder) >= 0) {
            for (i = 0; i < DataParser.ACCEL_TOTAL; i++)
                parser.setAccels(i, holder);

            readings.add(parser.getAccels());
        }

        return new Gesture(name, readings);
    }
}
