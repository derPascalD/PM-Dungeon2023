package graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import controller.ScreenController;
import ecs.entities.characterclasses.Tank;
import graphic.hud.FontBuilder;
import graphic.hud.ScreenButton;
import graphic.hud.TextButtonListener;
import graphic.hud.TextButtonStyleBuilder;
import starter.Game;
import tools.Point;

public class CharacterSelect<T extends Actor> extends ScreenController<T> {

    private ScreenButton tankButton;

    public CharacterSelect() {
        super(new SpriteBatch());
        tankButton = new ScreenButton(
            "Tank Klasse",
            new Point(200, 200),
            new TextButtonListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Game.removeEntity(Game.getHero().get());
                    Game.setHero(new Tank());
                    hideMenu();
                }
            },
            new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                .setFontColor(Color.GREEN)
                .setOverFontColor(Color.RED)
                .build());
        add((T) tankButton);
    }

    private void hideMenu() {
        tankButton.setVisible(false);
    }

}
