package lpoo.pokemonascii;

import lpoo.pokemonascii.gui.GameView;
import lpoo.pokemonascii.rules.state.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StateTest {
    private Battle battle;
    private World world;
    private PokemonSummary summary;
    private GameState gameState;
    private State state;

    @Before
    public void init() throws IOException, SAXException, ParserConfigurationException{
        GameView gui = Mockito.mock(GameView.class);
        gameState = new GameState(gui);
        battle = Mockito.mock(Battle.class);
        world = gameState.getWorld();
        state = battle;
        summary = Mockito.mock(PokemonSummary.class);
    }

    @Test
    public void test(){
        assertEquals(world, gameState.getState());

        gameState.setState(battle);
        assertEquals(battle, gameState.getState());

        gameState.setState(world);
        assertEquals(world, gameState.getState());

        gameState.setState(state);
        assertEquals(state, gameState.getState());

        gameState.setState(summary);
        assertEquals(summary, gameState.getState());

        gameState.setState(null);
        assertNull(gameState.getState());
    }
}
