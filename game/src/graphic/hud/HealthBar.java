package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import controller.ScreenController;
import ecs.entities.Entity;
import tools.Constants;
import tools.Point;

public class HealthBar<T extends Actor> extends ScreenController<T> {

    /** Creates a new HealthBar with a new Spritebatch */
    public HealthBar() {
        this(new SpriteBatch());
    }

    public static ScreenText healingBar;

    /** Creates a new HealthBar with a given Spritebatch */
    public HealthBar(SpriteBatch batch) {
        super(batch);
        setupHealingBar();
    }

    // Create me a healing bar for the window
    private void setupHealingBar() {
        healingBar =
                new ScreenText(
                        "",
                        new Point((Constants.WINDOW_WIDTH / 2 - 60), Constants.WINDOW_HEIGHT - 60),
                        1F,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.PURPLE)
                                .build());
        healingBar.setFontScale(1F);
        add((T) healingBar);
    }

    /**
     * Aktualisiere Die Healingbar und setze wär grad geheilt wird und zeige die Hp an.
     *
     * @param e actual Entity
     * @param b healing active or not
     * @param currentHealthpoints HP from the Hero
     */
    public static void updateHealingBar(Entity e, boolean b, int currentHealthpoints) {

        if (b) {
            healingBar.setPosition(Constants.WINDOW_WIDTH / 2 - 80, Constants.WINDOW_HEIGHT - 60);
            healingBar.setText(
                    "Healing Active : "
                            + e.getClass().getSimpleName()
                            + " : "
                            + currentHealthpoints);
        } else {
            healingBar.setText("");
        }
    }
}
