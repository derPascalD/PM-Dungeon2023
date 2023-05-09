package graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import controller.ScreenController;
import ecs.components.HealthComponent;
import ecs.entities.Hero;
import graphic.hud.FontBuilder;
import graphic.hud.LabelStyleBuilder;
import graphic.hud.ScreenText;
import starter.Game;
import tools.Point;

public class IngameUI<T extends Actor> extends ScreenController<T> {

    private static ScreenText screenText;
    private HealthComponent hp;

    public IngameUI() {
        super(new SpriteBatch());
        setupHPBar();
    }

    /**
     * Visualizes the Healthpoints of the Player on the Screen
     *
     */
    private void setupHPBar() {
        Hero d = (Hero) Game.getHero().get();
        hp = (HealthComponent) d.getComponent(HealthComponent.class).get();
        screenText =
            new ScreenText(
                "Healthpoints: " + hp.getCurrentHealthpoints(),
                new Point(10, 10),
                2,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.RED)
                    .build());
        add((T) screenText);
    }

    /**
     * Gets called when Healthpoints of the Hero gets updated
     * @param newHealthPoints new Healthpoints of the Hero
     */
    public static void updateHPBar(int newHealthPoints) {
        screenText.setText("Healthpoints: " + newHealthPoints);
    }

}
