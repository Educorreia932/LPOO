import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        Game game = new Game();

        game.run();
    }
}
