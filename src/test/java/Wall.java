import com.googlecode.lanterna.graphics.TextGraphics;

public class Wall extends Element {
    Wall(int x, int y, String character) {
        super(x, y, character);
    }

    public Wall(int x, int y) {
        super(x, y, "\u2588");
    }
}
