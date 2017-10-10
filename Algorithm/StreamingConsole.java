import javax.xml.crypto.Data;

/**
 * Learner
 *
 * @author Arshan Hashemi
 * @version 5/24/17
 */
public class StreamingConsole {

    public static void main(String args[]) {

        // USE INPUT STREAM READER AVAILABLE()

        DataParser parser = new DataParser();
        SlidingWindow window = new SlidingWindow(DataParser.WINDOW_SIZE, DataParser.ACCEL_TOTAL);

        try {
            parser.binaryParser(System.in, window);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}
