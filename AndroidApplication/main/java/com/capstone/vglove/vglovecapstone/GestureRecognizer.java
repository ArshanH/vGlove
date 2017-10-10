package com.capstone.vglove.vglovecapstone;

import java.io.InputStream;

/**
 * Gesture Recognizer:
 *
 * @author Arshan Hashemi
 * @version 6/1/17
 */
public class GestureRecognizer {

    private DataParser parser;

    public GestureRecognizer(InputStream stream) {
        parser = new DataParser(stream);
    }

    public String getGesture(byte[] reading) {
        try {
            return parser.binaryParser(reading);
        } catch(Exception e) {
            return null; //Window size exception never thrown
        }
    }


}
