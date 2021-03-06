package lpoo.pokemonascii.rules;

import lpoo.pokemonascii.data.BattleModel;
import lpoo.pokemonascii.data.SoundEffects.AttackSound;
import lpoo.pokemonascii.data.SoundEffects.CatchSound;
import lpoo.pokemonascii.data.SoundEffects.SelectSound;
import lpoo.pokemonascii.data.SoundEffects.SoundEffect;
import lpoo.pokemonascii.data.options.Option;
import lpoo.pokemonascii.data.options.battle.BattleOptionsMenuModel;
import lpoo.pokemonascii.data.options.fight.FightOption;
import lpoo.pokemonascii.data.options.fight.FightOptionsMenuModel;
import lpoo.pokemonascii.data.pokemon.Pokemon;
import lpoo.pokemonascii.data.pokemon.PokemonMove;
import lpoo.pokemonascii.data.pokemon.PokemonStats;
import lpoo.pokemonascii.gui.BattleView;
import lpoo.pokemonascii.rules.commands.*;
import lpoo.pokemonascii.rules.state.GameState;

import java.util.List;
import java.util.Random;

public class BattleController implements Controller {
    public enum OptionsMenu {
        BATTLE,
        FIGHT
    }

    private BattleView gui;
    private BattleModel battle;
    private OptionsMenuController options;
    private GameState.Gamemode gamemode;
    private SoundEffect attackSound;
    private SoundEffect selectSound;
    private SoundEffect catchSound;

    public BattleController(BattleView gui, BattleModel battle) {
        this.gui = gui;
        this.battle = battle;
        options = new OptionsMenuController(battle.getOptions());
        gamemode = GameState.Gamemode.BATTLE;
        attackSound = new AttackSound();
        selectSound = new SelectSound();
        catchSound = new CatchSound();
    }


    public GameState.Gamemode start(GameState game) {

        if(battle.getTrainerPokemon().getCurrentHealth() == 0){
            battle.getTrainerPokemon().setHP(1); //So the player can enter the battle
        }

        while (gamemode == GameState.Gamemode.BATTLE) {
            gui.draw();

            Command command;

            if (battle.getCurrentTurn() == BattleModel.Turn.TRAINER) 
                command = gui.getNextCommand(this);

            else {
                Random rand = new Random();
                List<PokemonMove> moves = battle.getAdversaryPokemon().getMoves();
                int i = rand.nextInt(moves.size() - 1);
                command = new UsePokemonMoveCommand(this, battle.getAdversaryPokemon(), moves.get(i));
                changeTurn();
            }

            command.execute();

            if (pokemonDied())
                gamemode = GameState.Gamemode.WORLD;
        }

        return gamemode;
    }

    @Override
    public void setGamemode(GameState.Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    public boolean pokemonDied() {
        return battle.getTrainerPokemon().getCurrentHealth() == 0 || battle.getAdversaryPokemon().getCurrentHealth() == 0;
    }

    public void usePokemonMove(Pokemon pokemon, PokemonMove move) {
        if (pokemon.getFacingDirection() == Pokemon.FacingDirection.BACK)
            move.execute(battle.getAdversaryPokemon());

        else if (pokemon.getFacingDirection() == Pokemon.FacingDirection.FRONT)
            move.execute(battle.getTrainerPokemon());
    }

    public OptionsMenuController getOptions() {
        return options;
    }

    public void executeOption(Option selectedOption) {
        selectSound.play();

        if (battle.getOptions() instanceof BattleOptionsMenuModel){
            switch (selectedOption.getName()) {
                case "FIGHT":
                    setOptionsMenu(OptionsMenu.FIGHT);
                    break;
                case "BAG":
                    new CatchPokemonCommand(this).execute();
                    break;
                case "POKEMON":
                    new ChangePokemonCommand(this).execute();
                    break;
                case "RUN":
                    new ChangedStateCommand(this, GameState.Gamemode.WORLD).execute();
                    break;
            }
        }
        else if (battle.getOptions() instanceof FightOptionsMenuModel && !((FightOption) selectedOption).getMove().getName().equals("-")) {
            attackSound.play();
            usePokemonMove(battle.getTrainerPokemon(), ((FightOption) selectedOption).getMove());
            setOptionsMenu(OptionsMenu.BATTLE);
            changeTurn();
        }
    }

    public void setOptionsMenu(BattleController.OptionsMenu options) {
        switch (options) {
            case BATTLE:
                battle.setOptions(new BattleOptionsMenuModel());
                break;
            case FIGHT:
                battle.setOptions(new FightOptionsMenuModel(battle.getTrainerPokemon()));
                break;
        }

        this.options.setOptions(battle.getOptions());
        gui.setOptionsMenuRenderer(options);
    }

    public void changeTurn() {
        if (battle.getCurrentTurn() == BattleModel.Turn.TRAINER)
            battle.setCurrentTurn(BattleModel.Turn.ADVERSARY);

        else
            battle.setCurrentTurn(BattleModel.Turn.TRAINER);
    }

    public void playerCaughtPokemon() {
        Random rand = new Random();

        final double HP_MAX = battle.getAdversaryPokemon().getStat(PokemonStats.Stat.HP);
        final double HP_CURRENT = battle.getAdversaryPokemon().getCurrentHealth();
        final double CATCH_RATE = 255;
        final double BONUS_BALL = 1;
        final double BONUS_STATUS = 1;

        double a = ((3 * HP_MAX - 2 * HP_CURRENT) * CATCH_RATE * BONUS_BALL) / (3 * HP_MAX) * BONUS_STATUS;
        int b = (int) (1048560.0 / Math.sqrt(Math.sqrt(16711680.0 / a)));

        for (int i = 0; i < 4; i++)
            if (rand.nextInt(65536) >= b) {
                changeTurn();
                return;
            }
        catchSound.play();
        battle.getPlayer().addPokemon(battle.getAdversaryPokemon());
        new ChangedStateCommand(this, GameState.Gamemode.WORLD).execute();

    }

    public void changePlayerPokemon() {
        battle.setAdversaryPokemon();
    }

    public BattleView getGui() {
        return gui;
    }

    public BattleModel getModel() {
        return battle;
    }
}
