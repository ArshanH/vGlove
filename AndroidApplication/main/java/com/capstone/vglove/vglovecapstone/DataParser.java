package com.capstone.vglove.vglovecapstone;

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

//public static final int ACCEL_TOTAL 2;

public class DataParser {
    public static final int ACCEL_TOTAL = 15;
    public static final int WINDOW_SIZE = 5;

    ArrayList<Gesture> gesture = new ArrayList<Gesture>(0);
    SlidingWindow window = new SlidingWindow(WINDOW_SIZE, ACCEL_TOTAL);
    Matrix[] matrix = new Matrix[ACCEL_TOTAL];

    private double[][] accels = new double[ACCEL_TOTAL][3];

    public DataParser() {
        try {
            File file = new File("gestureLib.txt");
            populateGestures(file, gesture);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public DataParser(InputStream stream) {
        try {
            File file = new File("gestureLib.txt");
            populateGestures(stream, gesture);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Gesture> getGestures() {
        return gesture;
    }
    
    public double[][] getAccels() { return deepCopy(accels); }

    public void binaryParser(InputStream stream) throws IOException, Exception {

        byte holder[] = new byte[ACCEL_TOTAL * 4];

        while (true) {
            while (stream.available() >= 60) {
                stream.read(holder);
                //System.out.println(Arrays.toString(holder));

                for (int i = 0; i < ACCEL_TOTAL; i++)
                    setAccels(i, holder);

                window.insert(deepCopy(accels));
                if (window.getSize() >= WINDOW_SIZE) {
                    boolean flagG = false;
                    //System.out.println("Registered as Palmup: " + palmup.equals(window.getSlidingAvg()));
                    for (Gesture g : gesture) {
                        if (g.equals(window.getSlidingAvg())) {
                            System.out.println("Registered as " + g.getName());
                            flagG = true;
                        }

                        //System.out.println("Registered as " + g.getName() + ": " + g.equals(window.getSlidingAvg()));
                    }
                    if (!flagG) {
                        System.out.println(".");
                    }
                }
            }
        }
    }

    public String binaryParser(byte[] reading) throws IOException, Exception {

        for (int i = 0; i < ACCEL_TOTAL; i++)
            setAccels(i, reading);

        window.insert(deepCopy(accels));
        if (window.getSize() >= WINDOW_SIZE) {
            boolean flagG = false;
            for (Gesture g : gesture) {
                if (g.equals(window.getSlidingAvg())) {
                    //System.out.println("Registered as " + g.getName());
                    return g.getName();
                }
            }
        }

        return null;
    }


    public void printHolder(byte holder[]) {
        System.out.print("Accelerometer " + holder[0]);
        System.out.print(": " + holder[1]);
        System.out.print(" " + holder[2]);
        System.out.println(" " + holder[3]);
    }

    public void setAccels(int i, byte holder[]) {
        int holder_i = i * 4;
        int accel_i = holder[holder_i];

        accels[accel_i][0] = holder[holder_i + 1];
        accels[accel_i][1] = holder[holder_i + 2];
        accels[accel_i][2] = holder[holder_i + 3];
    }

    public void printAccels(int index) {
        System.out.print("Accelerometer: " + (index + 1));
        System.out.print(": " + accels[index][0]);
        System.out.print(" " + accels[index][1]);
        System.out.println(" " + accels[index][2]);
    }

    public void populateGestures(File file, ArrayList<Gesture> gesture) throws Exception {
        String name;
        double[][] max = new double[15][3];
        double[][] min = new double[15][3];
        Scanner in = new Scanner(file);
        while (in.hasNext()) {
            name = in.next();
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 3; j++) {
                    min[i][j] = in.nextFloat();
                }
            }
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 3; j++) {
                    max[i][j] = in.nextFloat();
                }
            }
            gesture.add(new Gesture(new String(name), deepCopy(min), deepCopy(max)));
        }
    }

    public void populateGestures(InputStream stream, ArrayList<Gesture> gesture) throws Exception {
        String name;
        double[][] max = new double[15][3];
        double[][] min = new double[15][3];
        Scanner in = new Scanner(stream);
        while (in.hasNext()) {
            name = in.next();
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 3; j++) {
                    min[i][j] = in.nextFloat();
                }
            }
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 3; j++) {
                    max[i][j] = in.nextFloat();
                }
            }
            gesture.add(new Gesture(name, deepCopy(min), deepCopy(max)));
        }
    }


    private static double[][] deepCopy(double[][] original) {
            if (original == null)
                return null;

            final double[][] result = new double[original.length][];
            for (int i = 0; i < original.length; i++)
                result[i] = Arrays.copyOf(original[i], original[i].length);
            return result;
        }
    }
