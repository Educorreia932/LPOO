package lpoo.pokemonascii.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import lpoo.pokemonascii.data.WorldModel;
import lpoo.pokemonascii.gui.commands.*;
import lpoo.pokemonascii.gui.renderers.BackgroundRenderer;
import lpoo.pokemonascii.gui.renderers.PlayerRenderer;
import lpoo.pokemonascii.rules.WorldController;

import java.awt.*;
import java.io.IOException;

public class WorldView {
    private Screen screen;
    private TextGraphics graphics;
    private WorldModel world;
    private BackgroundRenderer backgroundRenderer;
    private PlayerRenderer playerRenderer;

    public WorldView(Screen screen, TextGraphics graphics, WorldModel world) throws IOException {
        this.screen = screen;
        this.graphics = graphics;
        this.world = world;

        backgroundRenderer = new BackgroundRenderer("room");
        playerRenderer = new PlayerRenderer(world.getPlayer());
    }

    public void drawWorld() throws IOException {
        screen.clear();

        backgroundRenderer.draw(graphics);
        playerRenderer.draw(graphics);

        screen.refresh();
    }

    public static KeyStroke getPressedKey(Screen screen) throws IOException {
        KeyStroke key = screen.readInput();

        return key;
    }

    public Command getNextCommand(WorldController world) throws IOException {
        KeyStroke pressedKey = getPressedKey(screen);

        switch (pressedKey.getKeyType()) {
            case ArrowUp:
                return new PlayerMoveUpCommand(world);
            case ArrowDown:
                return new PlayerMoveDownCommand(world);
            case ArrowRight:
                return new PlayerMoveRightCommand(world);
            case ArrowLeft:
                return new PlayerMoveLeftCommand(world);
            case EOF:
                return new QuitCommand(screen);
            case Character:
                switch (pressedKey.getCharacter()) {
                    case 'q':
                        return new QuitCommand(screen);
                }
        }

        return new DoNothingCommand();
    }
}
