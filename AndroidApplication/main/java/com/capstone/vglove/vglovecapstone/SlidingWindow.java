package com.capstone.vglove.vglovecapstone;

/**
 * Circular Buffer based Sliding Window
 *
 * @author Arshan Hashemi
 * @version 5/10/17
 */
public class SlidingWindow {

    private int maxSize;
    private int front = 0;
    private int rear = -1;
    private int bufLen = 0;
    private int accelTotal;

    private double[][][] buf;

    private double[][] slidingAvg;

    public SlidingWindow(int size, int accels) {
        maxSize = size;
        buf = new double[size][accels][3];
        slidingAvg = new double[accels][3];
        accelTotal = accels;
    }

    // Accessor Functions
    public int getSize() { return bufLen; }
    public boolean isEmpty() { return bufLen == 0; }
    public boolean isFull() { return bufLen == maxSize; }
    public double[][][] getWindow() { return buf; }

    public double[][] getSlidingAvg() throws Exception {

        if (!isFull())
            throw new Exception("Window must be full to calculate average.");

        return slidingAvg;
    }

    public void clear() {
        front = rear = bufLen = 0;
        buf = new double[maxSize][accelTotal][3];
    }

    public void insert(double[][] reading) {

        updateAvg(reading);
        // if window is full remove first element
        if (isFull())
            front = (front + 1) % maxSize;
        else
            bufLen++;

        rear = (rear + 1) % maxSize;
        buf[rear] = reading;
    }

    private void updateAvg(double[][] reading) {
        int i,j;

        for (i = 0; i < accelTotal; i++) {
            for (j = 0; j < 3; j++) {
                if (isFull())
                    slidingAvg[i][j] -= buf[front][i][j] / (double) maxSize;
                slidingAvg[i][j] += reading[i][j] / (double) maxSize;
            }
        }
    }

}
