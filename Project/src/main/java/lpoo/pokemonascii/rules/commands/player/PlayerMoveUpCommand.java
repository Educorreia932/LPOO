package lpoo.pokemonascii.rules.commands.player;

import lpoo.pokemonascii.data.Player;
import lpoo.pokemonascii.data.Position;
import lpoo.pokemonascii.rules.WorldController;
import lpoo.pokemonascii.rules.commands.Command;

public class PlayerMoveUpCommand implements Command {
    private WorldController controller;

    public PlayerMoveUpCommand(WorldController world) {
        this.controller = world;
    }

    @Override
    public void execute() {
        controller.movePlayer(Position.Direction.UP);
        controller.setPlayerState(Player.State.BACK);
    }
}
