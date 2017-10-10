import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Learner
 *
 * @author Arshan Hashemi
 * @version 5/16/17
 */
public class LearnerTest {
    @Test
    public void MMF_self_test() throws Exception {
        DataParser parser = new DataParser();
        String fileName = "5-11TestData/michaelmiddlefinger.bin";
        Gesture middleFing = Learner.createGesture(fileName);
        int correct = 0, incorrect = 0;


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

        for (double[][] reading: readings) {
            if (middleFing.equals(reading))
                correct++;
            else
                incorrect++;
        }

        System.out.println("Correct: " + correct);
        System.out.println("Incorrect: " + incorrect);
    }

    @Test
    public void SMF_MMF_test() throws Exception {
        DataParser parser = new DataParser();
        String gestFile = "5-11TestData/michaelmiddlefinger.bin";
        String fileName = "5-11TestData/stevenmiddlefinger.bin";
        Gesture middleFing = Learner.createGesture(gestFile);
        int correct = 0, incorrect = 0;


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

        for (double[][] reading: readings) {
            if (middleFing.equals(reading))
                correct++;
            else
                incorrect++;
        }

        System.out.println("Correct: " + correct);
        System.out.println("Incorrect: " + incorrect);
    }

    @Test
    public void MPU_SPU_test() throws Exception {
        DataParser parser = new DataParser();
        String gestFile = "5-11TestData/michaelpalmup.bin";
        String fileName = "5-11TestData/stevenpalmup.bin";
        Gesture middleFing = Learner.createGesture(gestFile);
        int correct = 0, incorrect = 0;


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

        for (double[][] reading: readings) {
            if (middleFing.equals(reading))
                correct++;
            else
                incorrect++;
        }

        System.out.println("Correct: " + correct);
        System.out.println("Incorrect: " + incorrect);

    }

    @Test
    public void MRO_SRO_test() throws Exception {
        DataParser parser = new DataParser();
        String gestFile = "5-11TestData/michaelrockon.bin";
        String fileName = "5-11TestData/stevenrockon.bin";
        Gesture middleFing = Learner.createGesture(gestFile);
        int correct = 0, incorrect = 0;

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

        i = 0;
        for (double[][] reading: readings) {
            if (middleFing.equals(reading))
                correct++;
            else
                incorrect++;
        }

        System.out.println("Correct: " + correct);
        System.out.println("Incorrect: " + incorrect);
    }

    @Test
    public void SRO_MRO_test() throws Exception {
        DataParser parser = new DataParser();
        String gestFile = "5-11TestData/stevenrockon.bin";
        String fileName = "5-11TestData/michaelrockon.bin";
        Gesture middleFing = Learner.createGesture(gestFile);
        int correct = 0, incorrect = 0;

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

        i = 0;
        for (double[][] reading: readings) {
            if (middleFing.equals(reading))
                correct++;
            else
                incorrect++;
        }

        System.out.println("Correct: " + correct);
        System.out.println("Incorrect: " + incorrect);
    }

    @Test
    public void MRO_CRO_MORE_DATA_test() throws Exception {
        DataParser parser = new DataParser();
        String gestFile = "rawdata/chrisrockon.bin";
        String fileName = "rawdata/rockon.bin";
        Gesture middleFing = Learner.createGesture(gestFile);
        int correct = 0, incorrect = 0;
        SlidingWindow window = new SlidingWindow(100, 15);

        byte holder[] = new byte[DataParser.ACCEL_TOTAL * 4];
        ArrayList<double[][]> readings = new ArrayList<>(10);
        int i;

        File binaryFile = new File(fileName);
        FileInputStream stream = new FileInputStream(binaryFile);

        while (stream.read(holder) >= 0) {
            for (i = 0; i < DataParser.ACCEL_TOTAL; i++)
                parser.setAccels(i, holder);
            readings.add(parser.getAccels());
            window.insert(parser.getAccels());
        }

        i = 0;
        for (double[][] reading: readings) {
            if (middleFing.equals(reading))
                correct++;
            else
                incorrect++;
        }

        System.out.println("Correct: " + correct);
        System.out.println("Incorrect: " + incorrect);
        System.out.println();
        //System.out.println("Average result: " + middleFing.equals(window.getSlidingAvg()));

    }

    @Test
    public void MRO_CRO_SPLIT_DATA_test() throws Exception {
        DataParser parser = new DataParser();
        String gestFile = "rawdata/split_rockon_train.bin";
        String fileName = "rawdata/split_rockon_test.bin";
        Gesture middleFing = Learner.createGesture(gestFile);
        int correct = 0, incorrect = 0;
        SlidingWindow window = new SlidingWindow(100, 15);

        byte holder[] = new byte[DataParser.ACCEL_TOTAL * 4];
        ArrayList<double[][]> readings = new ArrayList<>(10);
        int i;

        File binaryFile = new File(fileName);
        FileInputStream stream = new FileInputStream(binaryFile);

        while (stream.read(holder) >= 0) {
            for (i = 0; i < DataParser.ACCEL_TOTAL; i++)
                parser.setAccels(i, holder);
            readings.add(parser.getAccels());
            window.insert(parser.getAccels());
        }

        i = 0;
        for (double[][] reading: readings) {
            if (middleFing.equals(reading))
                correct++;
            else
                incorrect++;
        }

        System.out.println("Correct: " + correct);
        System.out.println("Incorrect: " + incorrect);
        System.out.println();
        System.out.println("Average result: " + middleFing.equals(window.getSlidingAvg()));

    }

    @Test
    public void WRONG_GESTURE_test() throws Exception {
        DataParser parser = new DataParser();
        String gestFile = "rawdata/chrisrockon.bin";
        String fileName = "rawdata/palmup.bin";
        Gesture middleFing = Learner.createGesture(gestFile);
        int correct = 0, incorrect = 0;
        SlidingWindow window = new SlidingWindow(100, 15);

        byte holder[] = new byte[DataParser.ACCEL_TOTAL * 4];
        ArrayList<double[][]> readings = new ArrayList<>(10);
        int i;

        File binaryFile = new File(fileName);
        FileInputStream stream = new FileInputStream(binaryFile);

        while (stream.read(holder) >= 0) {
            for (i = 0; i < DataParser.ACCEL_TOTAL; i++)
                parser.setAccels(i, holder);
            readings.add(parser.getAccels());
            window.insert(parser.getAccels());
        }

        i = 0;
        for (double[][] reading: readings) {
            if (middleFing.equals(reading))
                correct++;
            else
                incorrect++;
        }

        System.out.println("Correct: " + correct);
        System.out.println("Incorrect: " + incorrect);
        System.out.println();
        //System.out.println("Average result: " + middleFing.equals(window.getSlidingAvg()));

    }

    @Test
    public void THREE_DATA_test() throws Exception {
        DataParser parser = new DataParser();
        String gestFile1 = "rawdata/chrisrockon.bin";
        String gestFile2 = "rawdata/rockon.bin";
        String fileName = "5-11TestData/stevenrockon.bin";

        Gesture rockOn = Learner.createGesture(gestFile1);
        rockOn.train(gestFile2);

        int correct = 0, incorrect = 0;
        SlidingWindow window = new SlidingWindow(100, 15);

        byte holder[] = new byte[DataParser.ACCEL_TOTAL * 4];
        ArrayList<double[][]> readings = new ArrayList<>(10);
        int i;

        File binaryFile = new File(fileName);
        FileInputStream stream = new FileInputStream(binaryFile);

        while (stream.read(holder) >= 0) {
            for (i = 0; i < DataParser.ACCEL_TOTAL; i++)
                parser.setAccels(i, holder);
            readings.add(parser.getAccels());
            window.insert(parser.getAccels());
        }

        i = 0;
        for (double[][] reading: readings) {
            if (rockOn.equals(reading))
                correct++;
            else
                incorrect++;
        }

        System.out.println("Correct: " + correct);
        System.out.println("Incorrect: " + incorrect);
        System.out.println();
        //System.out.println("Average result: " + middleFing.equals(window.getSlidingAvg()));

    }

    @Test
    public void SPU_THREE_DATA_test() throws Exception {
        DataParser parser = new DataParser();
        String gestFile1 = "rawdata/chrispalmup.bin";
        String gestFile2 = "rawdata/palmup.bin";
        String fileName = "5-11TestData/michaelpalmup.bin";

        Gesture rockOn = Learner.createGesture(gestFile1);
        rockOn.train(gestFile2);

        int correct = 0, incorrect = 0;
        SlidingWindow window = new SlidingWindow(100, 15);

        byte holder[] = new byte[DataParser.ACCEL_TOTAL * 4];
        ArrayList<double[][]> readings = new ArrayList<>(10);
        int i;

        File binaryFile = new File(fileName);
        FileInputStream stream = new FileInputStream(binaryFile);

        while (stream.read(holder) >= 0) {
            for (i = 0; i < DataParser.ACCEL_TOTAL; i++)
                parser.setAccels(i, holder);
            readings.add(parser.getAccels());
            window.insert(parser.getAccels());
        }

        i = 0;
        for (double[][] reading: readings) {
            if (rockOn.equals(reading))
                correct++;
            else
                incorrect++;
        }

        System.out.println("Correct: " + correct);
        System.out.println("Incorrect: " + incorrect);
        System.out.println();
        //System.out.println("Average result: " + middleFing.equals(window.getSlidingAvg()));

    }


}