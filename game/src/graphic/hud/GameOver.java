package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ControllerLayer;
import controller.ScreenController;
import java.util.logging.Level;
import java.util.logging.Logger;
import starter.Game;
import tools.Constants;
import tools.Point;

public class GameOver<T extends Actor> extends ScreenController<T> {

    private final Logger gameOverLogger = Logger.getLogger(getClass().getName());

    /** Creates a new GameOver with a new Spritebatch */
    public GameOver() {
        this(new SpriteBatch());
    }

    /** Creates a new GameOver with a given Spritebatch */
    public GameOver(SpriteBatch batch) {
        super(batch);
        createGameOverMenue();
    }

    private void createGameOverMenue() {
        createGameOverScreen();
        restartGame();
        quitGameButton();
    }

    private void createGameOverScreen() {
        gameOverLogger.log(Level.INFO, "GameOver Menue is open");
        ScreenText screenText =
                new ScreenText(
                        "Game Over",
                        new Point(0, 0),
                        3,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.RED)
                                .build());
        screenText.setFontScale(3);
        screenText.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - 70,
                (Constants.WINDOW_HEIGHT) / 2F + 80,
                Align.center | Align.bottom);
        add((T) screenText);
    }

    private void restartGame() {
        ScreenButton restartButton = new ScreenButton(
            "Restart Game",
            new Point(0, 0),
            new TextButtonListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameOverLogger.log(Level.INFO, "Restart Game");
                    Game.restartGame();
                }
            },
            new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                .setFontColor(Color.WHITE)
                .setOverFontColor(Color.GOLDENROD)
                .build());
        restartButton.setPosition(
            (Constants.WINDOW_WIDTH) / 2f - restartButton.getWidth(),
            (Constants.WINDOW_HEIGHT) / 2f + restartButton.getHeight(),
                Align.center | Align.bottom);
        add((T) restartButton);
    }

    private void quitGameButton() {
        ScreenButton quitButton = new ScreenButton(
            "Quit game",
            new Point(0, 0),
            new TextButtonListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameOverLogger.log(Level.SEVERE, "Game quit");
                    System.exit(0);
                }
            },
            new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                .setFontColor(Color.WHITE)
                .setOverFontColor(Color.GOLDENROD)
                .build()
        );
        quitButton.setPosition(
            (Constants.WINDOW_WIDTH) / 2f + quitButton.getWidth(),
            (Constants.WINDOW_HEIGHT) / 2f + quitButton.getHeight(),
                Align.center | Align.bottom);
        add((T) quitButton);
        hideMenu();
    }

    /** shows the Menu */
    public void showMenu() {
        this.forEach((Actor s) -> s.setVisible(true));
    }

    /** hides the Menu */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }
}
