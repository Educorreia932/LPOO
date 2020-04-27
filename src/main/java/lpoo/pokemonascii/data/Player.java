package lpoo.pokemonascii.data;

import lpoo.pokemonascii.data.elements.CollidingElement;
import lpoo.pokemonascii.data.pokemon.Pokemon;
import sun.jvm.hotspot.tools.soql.SOQL;

import javax.net.ssl.SSLContext;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.List;

public class Player extends CollidingElement {
    private String name;
    private List<Pokemon> pokemons;
    private State state;

    public enum State {
        FRONT,
        BACK,
        RIGHT,
        LEFT,
    }

    public Player() {
        super(111,70, 23,19);
        state = State.FRONT;
    }

    public Position getPosition(Position.Direction direction) {
        switch (direction) {
            case UP:
                return getPosition().up(16);
            case DOWN:
                return getPosition().down(16);
            case RIGHT:
                return getPosition().right(26);
            case LEFT:
                return getPosition().left(26);
        }

        return getPosition();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}