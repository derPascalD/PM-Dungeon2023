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
import tools.Constants;
import tools.Point;

public class IngameUI<T extends Actor> extends ScreenController<T> {

    private static ScreenText attackButton;
    private static ScreenText equiqMelee;
    private static ScreenText hpScreen;
    private static ScreenText skillsScreen;

    private Hero hero;
    private HealthComponent hp;

    // Mana
    private int ma;

    public IngameUI() {
        super(new SpriteBatch());
        hero = (Hero) Game.getHero().get();
        setupHPBar();
        setupSkill();
        attackButtonHero();
        setEquiqMelee();
    }

    /** Visualizes the Healthpoints of the Player on the Screen */
    private void setupHPBar() {

        hp = (HealthComponent) hero.getComponent(HealthComponent.class).get();
        hpScreen =
                new ScreenText(
                        "Healthpoints: " + hp.getCurrentHealthpoints(),
                        new Point(10, 10),
                        2,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.RED)
                                .build());
        add((T) hpScreen);
    }

    private void setupSkill() {

        skillsScreen =
                new ScreenText(
                        "-- Skills unlocked --\n 1. " + "-" + "\n 2. " + "-" + "\n 3. " + "-",
                        new Point(Constants.WINDOW_WIDTH - 130, 0),
                        3,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.MAGENTA)
                                .build());

        add((T) skillsScreen);
    }

    /**
     * Gets called when Healthpoints of the Hero gets updated
     *
     * @param newHealthPoints new Healthpoints of the Hero
     */
    public static void updateHPBar(int newHealthPoints) {
        hpScreen.setText("Healthpoints: " + newHealthPoints);
    }

    public static void updateSkillsBar(String skill1, String skill2, String skill3) {
        skillsScreen.setText(
                "-- Skills unlocked --\n 1. " + skill1 + "\n 2. " + skill2 + "\n 3. " + skill3);
    }

    /*
    Close combat attack indicator
    */
    private void attackButtonHero() {
        attackButton =
                new ScreenText(
                        "Attack: R",
                        new Point(Constants.WINDOW_WIDTH - 100, Constants.WINDOW_HEIGHT - 40),
                        2,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.GOLD)
                                .build());
        add((T) attackButton);
    }

    /*
    Melee weapon equip indicator
    */
    private void setEquiqMelee() {
        equiqMelee =
            new ScreenText(
                "Melee equip: 4",
                new Point(Constants.WINDOW_WIDTH - 100, Constants.WINDOW_HEIGHT - 20),
                2,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.GOLD)
                    .build());
        add((T) equiqMelee);
    }
}
