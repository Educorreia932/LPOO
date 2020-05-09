package lpoo.pokemonascii.gui.renderers.text;

import com.googlecode.lanterna.graphics.TextGraphics;
import lpoo.pokemonascii.data.Position;
import lpoo.pokemonascii.gui.Sprite;
import lpoo.pokemonascii.gui.renderers.Renderer;

import static lpoo.pokemonascii.gui.Sprite.drawSprite;

public class CharRenderer implements Renderer {
    private Position position;
    private Sprite sprite;

    public CharRenderer(char c, Position position) {
        this.position = position;

        if (Character.isLowerCase(c))
            sprite = new Sprite("font\\lower_case\\" + c);

        else if (Character.isUpperCase(c))
            sprite = new Sprite("font\\upper_case\\" + c);

        else if (Character.isDigit(c))
            sprite = new Sprite("font\\numbers\\" + c);

        else if (!Character.isAlphabetic(c)) {
            switch (c) {
                case '/':
                    sprite = new Sprite("font\\symbols\\slash");
                    break;
                case ' ':
                    sprite = new Sprite("font\\symbols\\space");
                    break;
                default:
                    sprite = new Sprite("font\\symbols\\" + c);
                    break;
            }
        }
    }

    @Override
    public void draw(TextGraphics graphics) {
        drawSprite(sprite, position, graphics, true);
    }

    public int getWidth() {
        return sprite.getWidth();
    }
}
