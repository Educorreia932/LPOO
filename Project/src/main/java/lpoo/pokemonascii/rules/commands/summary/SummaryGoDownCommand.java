package lpoo.pokemonascii.rules.commands.summary;

import lpoo.pokemonascii.data.Position;
import lpoo.pokemonascii.rules.PokemonSummaryController;
import lpoo.pokemonascii.rules.commands.Command;

public class SummaryGoDownCommand implements Command {
    PokemonSummaryController controller;

    public SummaryGoDownCommand(PokemonSummaryController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.changeSelectedPokemon(Position.Direction.DOWN);
    }
}
