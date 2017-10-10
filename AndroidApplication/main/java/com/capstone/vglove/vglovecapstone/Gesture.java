package com.capstone.vglove.vglovecapstone;

import java.io.*;;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Learner
 *
 * @author Arshan Hashemi
 * @version 5/10/17
 */
public class Gesture {

    private double[][] min, max;
    private String name = new String();


    public Gesture(ArrayList<double[][]> readings) {

        max = new double[15][3];
        min = new double[15][3];
        int i, j;

        for (i = 0; i < 15; i++)
            for (j = 0; j < 3; j++) {
                max[i][j] = Byte.MIN_VALUE;
                min[i][j] = Byte.MAX_VALUE;
            }

        for (double[][] reading : readings) {
            for (i = 0; i < 15; i++)
                for (j = 0; j < 3; j++) {
                    if (reading[i][j] > max[i][j])
                        max[i][j] = reading[i][j];
                    if (reading[i][j] < min[i][j])
                        min[i][j] = reading[i][j];
                }
        }
    }

    public Gesture(String name, ArrayList<double[][]> readings) {
        this.name = name;
        max = new double[15][3];
        min = new double[15][3];
        int i, j;

        for (i = 0; i < 15; i++)
            for (j = 0; j < 3; j++) {
                max[i][j] = Byte.MIN_VALUE;
                min[i][j] = Byte.MAX_VALUE;
            }

        for (double[][] reading : readings) {
            for (i = 0; i < 15; i++)
                for (j = 0; j < 3; j++) {
                    if (reading[i][j] > max[i][j])
                        max[i][j] = reading[i][j];
                    if (reading[i][j] < min[i][j])
                        min[i][j] = reading[i][j];
                }
        }
    }
    public Gesture (double[][] min, double[][] max) {
        this.max = max;
        this.min = min;
    }

    public Gesture (String name, double[][] min, double[][] max) {
        this.name = name;
        this.max = max;
        this.min = min;
    }

    public Gesture() {
        max = new double[15][3];
        min = new double[15][3];
        int i, j;

        for (i = 0; i < 15; i++)
            for (j = 0; j < 3; j++) {
                max[i][j] = Byte.MIN_VALUE;
                min[i][j] = Byte.MAX_VALUE;
            }

    }

    public double[][] getMin() { return min; }
    public double[][] getMax() { return max; }

    public boolean equals (byte[][] reading)
    {
        int i, j, under = 0, over = 0, total = 0;
        for (i = 0; i < 15; i++)
            for (j = 0; j < 3; j++) {
                total++;
                //System.out.println("Min: " + min[i][j] + ", Reading: " + reading[i][j] + ", Max: " + max[i][j]);
                if (reading[i][j] > max[i][j])
                    over++;
                    //return false;
                if (reading[i][j] < min[i][j])
                    //return false;
                    under++;
            }

        //System.out.printf("under/total: %d/%d, over/total: %d/%d\n", under, total, over, total);

        return (under == 0 && over == 0);
    }

    public boolean equals (double[][] reading)
    {
        int i, j, under = 0, over = 0, total = 0;
        for (i = 0; i < 15; i++)
            for (j = 0; j < 3; j++) {
                total++;
                //System.out.println("Min: " + min[i][j] + ", Reading: " + reading[i][j] + ", Max: " + max[i][j]);
                if (reading[i][j] > max[i][j])
                    //over++;
                    return false;
                if (reading[i][j] < min[i][j])
                    return false;
                    //under++;
            }

        //System.out.printf("under/total: %d/%d, over/total: %d/%d\n", under, total, over, total);

        //return (under == 0 && over == 0);
        return (true);
    }

    public void train (String fileName) throws Exception {
        DataParser parser = new DataParser();
        byte holder[] = new byte[DataParser.ACCEL_TOTAL * 4];
        ArrayList<double[][]> readings = new ArrayList<>(10);
        int i, j;

        File binaryFile = new File(fileName);
        FileInputStream stream = new FileInputStream(binaryFile);

        while (stream.read(holder) >= 0) {
            for (i = 0; i < DataParser.ACCEL_TOTAL; i++)
                parser.setAccels(i, holder);

            readings.add(parser.getAccels());
        }

        for (double[][] reading : readings) {
            for (i = 0; i < 15; i++)
                for (j = 0; j < 3; j++) {
                    if (reading[i][j] > max[i][j])
                        max[i][j] = reading[i][j];
                    if (reading[i][j] < min[i][j])
                        min[i][j] = reading[i][j];
                }
        }

    }

    public void train (String name, String fileName) throws Exception {
        this.name = name;
        DataParser parser = new DataParser();
        byte holder[] = new byte[DataParser.ACCEL_TOTAL * 4];
        ArrayList<double[][]> readings = new ArrayList<>(10);
        int i, j;

        File binaryFile = new File(fileName);
        FileInputStream stream = new FileInputStream(binaryFile);

        while (stream.read(holder) >= 0) {
            for (i = 0; i < DataParser.ACCEL_TOTAL; i++)
                parser.setAccels(i, holder);

            readings.add(parser.getAccels());
        }

        for (double[][] reading : readings) {
            for (i = 0; i < 15; i++)
                for (j = 0; j < 3; j++) {
                    if (reading[i][j] > max[i][j])
                        max[i][j] = reading[i][j];
                    if (reading[i][j] < min[i][j])
                        min[i][j] = reading[i][j];
                }
        }

    }

    public void writeToFile(File file) throws Exception {
        FileWriter out = new FileWriter(file, true);

        out.write(name + "\n");

        for (int i = 0; i < DataParser.ACCEL_TOTAL; i++) {
            for (int j = 0; j < 3; j++)
                out.write(min[i][j] + " ");
            out.write("\n");
        }
        out.write("\n");
        for (int i = 0; i < DataParser.ACCEL_TOTAL; i++) {
            for (int j = 0; j < 3; j++)
                out.write(max[i][j] + " ");
            out.write("\n");
        }

        out.write("\n");
        out.close();
    }

    public void matrixConversion(Matrix[] matrix) {

        Vector minTemp = new Vector(), maxTemp = new Vector();

        for (int i = 0; i < DataParser.ACCEL_TOTAL; i++) {
            minTemp.update(min[i][0], min[i][1], min[i][2]);
            maxTemp.update(max[i][0], max[i][1], max[i][2]);

            minTemp = matrix[i].multiply(minTemp);
            maxTemp = matrix[i].multiply(maxTemp);

            min[i][0] = (double) minTemp.getX();
            min[i][1] = (double) minTemp.getY();
            min[i][2] = (double) minTemp.getZ();

            max[i][0] = (double) maxTemp.getX();
            max[i][1] = (double) maxTemp.getY();
            max[i][2] = (double) maxTemp.getZ();
        }
    }

    public String getName() {
        return name;
    }

    public void print() {
        System.out.println("\n" + name + "\nMin: ");
        for (int i = 0; i < 15; i++) {
            System.out.print(Arrays.toString(min[i]));
        }
        System.out.println();
        System.out.println("Max: ");
        for (int i = 0; i < 15; i++) {
            System.out.print(Arrays.toString(max[i]));
        }
        System.out.println();
    }

}
