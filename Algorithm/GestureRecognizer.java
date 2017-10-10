/**
 * Gesture Recognizer:
 *
 * @author Arshan Hashemi
 * @version 6/1/17
 */
public class GestureRecognizer {

    private DataParser parser;

    public GestureRecognizer() {
        parser = new DataParser();
    }

    public String getGesture(byte[] reading) {
        try {
            return parser.binaryParser(reading);
        } catch(Exception e) {
            return null; //Window size exception never thrown
        }
    }


}
