package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import tools.Constants;
import tools.Point;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameOver<T extends Actor> extends ScreenController<T> {

    private ScreenButton restartButton;
    private ScreenButton quitButton;
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

    public void createGameOverMenue() {
        createGameOverScreen();
        restartGame();
        quitGameButton();
    }

    private void createGameOverScreen() {
        gameOverLogger.log(Level.INFO,"GameOver Menue is open");
        ScreenText screenText =
                new ScreenText(
                        "Game Over",
                        new Point(0, 0),
                        4,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.RED)
                                .build());
        screenText.setFontScale(3);
        screenText.setPosition(
                (Constants.WINDOW_WIDTH) / 2f,
                (Constants.WINDOW_HEIGHT) / 2F,
                Align.center | Align.bottom);
        add((T) screenText);
        hideMenu();
    }

    private void restartGame() {
        restartButton =
                new ScreenButton(
                        "Restart Game",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                gameOverLogger.log(Level.INFO,"Game Restart");
                                // TODO Restart game
                            }
                        },
                        new TextButton.TextButtonStyle());
        restartButton.setColor(Color.WHITE);
        restartButton.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - 10,
                (Constants.WINDOW_HEIGHT) / 2F - 30,
                Align.center | Align.bottom);
        add((T) restartButton);
    }

    private void quitGameButton() {
        quitButton =
                new ScreenButton(
                        "Restart Game",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                gameOverLogger.log(Level.SEVERE,"Game quit");
                                // TODO Quit game
                            }
                        },
                        new TextButton.TextButtonStyle());
        quitButton.setColor(Color.WHITE);
        quitButton.setPosition(
                (Constants.WINDOW_WIDTH) / 2f + 10,
                (Constants.WINDOW_HEIGHT) / 2F - 30,
                Align.center | Align.bottom);
        add((T) quitButton);
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
