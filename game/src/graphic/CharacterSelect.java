package graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import ecs.entities.characterclasses.Archer;
import ecs.entities.characterclasses.Rouge;
import ecs.entities.characterclasses.Tank;
import graphic.hud.*;
import starter.Game;
import tools.Constants;
import tools.Point;

import java.util.ArrayList;

public class CharacterSelect<T extends Actor> extends ScreenController<T> {

    private ArrayList<ScreenButton> screenButtons;
    private ArrayList<ScreenImage> screenImages;
    private TextButton.TextButtonStyle classStyle;
    private static boolean selected = false;

    public CharacterSelect() {
        super(new SpriteBatch());
        screenImages = new ArrayList<>();
        screenButtons = new ArrayList<>();
        generateScreenImages();
        generateScreenButtons();

    }

    private void generateScreenButtons() {

        classStyle = new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
            .setFontColor(Color.GREEN)
            .setOverFontColor(Color.RED)
            .build();

        setupTankButton();
        setupArcherButton();
        setupRougeButton();
    }

    private void setupTankButton() {
        ScreenButton tankButton = new ScreenButton(
            "Tank:\nHP: 100\nMelee Damage: 1\n Slow but Tanky",
            new Point(0,0),
            new TextButtonListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Game.removeEntity(Game.getHero().get());
                    Game.setHero(new Tank());
                    Game.togglePause();
                    hideMenu();
                    selected = true;
                    addGameOverMenu();
                }
            },
            classStyle);
        tankButton.setPosition(
            105,
            (Constants.WINDOW_HEIGHT/1.7f),
            Align.center | Align.top
            );

        tankButton.setScale(1f,1f);
        tankButton.align(Align.bottomRight);
        add((T) tankButton);
        screenButtons.add(tankButton);
    }

    private void setupArcherButton() {
        ScreenButton archerButton = new ScreenButton(
            "Archer:\nHP: 30\nMelee Damage: 1\nRange Damage: 3\n Fast and High Damage",
            new Point(0,0),
            new TextButtonListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Game.removeEntity(Game.getHero().get());
                    Game.setHero(new Archer());
                    Game.togglePause();
                    hideMenu();
                    selected = true;
                    addGameOverMenu();
                }
            },
            classStyle);
        archerButton.setPosition(
            (Constants.WINDOW_WIDTH/3f)+105,
            (Constants.WINDOW_HEIGHT/1.7f),
            Align.center | Align.top
        );

        archerButton.setScale(1f,1f);
        archerButton.align(Align.bottomRight);
        add((T) archerButton);
        screenButtons.add(archerButton);
    }

    private void setupRougeButton() {
        ScreenButton rougeButton = new ScreenButton(
            "Rouge:\nHP: 20\nMelee Damage: 3\n Fast and High Damage",
            new Point(0,0),
            new TextButtonListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Game.removeEntity(Game.getHero().get());
                    Game.setHero(new Rouge());
                    Game.togglePause();
                    hideMenu();
                    selected = true;
                    addGameOverMenu();
                }
            },
            classStyle);
        rougeButton.setPosition(
            (Constants.WINDOW_WIDTH/3f)*2+105,
            (Constants.WINDOW_HEIGHT/1.7f),
            Align.center | Align.top
        );

        rougeButton.setScale(1f,1f);
        rougeButton.align(Align.bottomRight);
        add((T) rougeButton);
        screenButtons.add(rougeButton);
    }

    private void generateScreenImages() {
        ScreenImage sc;
        int xOffset = (Constants.WINDOW_WIDTH/3);
        for (int i = 0; i < 3; i++) {
            sc = new ScreenImage("characterselection/brown.png",new Point(xOffset*i, 0));
            sc.setSize((xOffset/2)+1,Constants.WINDOW_HEIGHT/2);
            screenImages.add(sc);
            add((T) sc);
        }
    }

    private void hideMenu() {
        for(ScreenButton sb: screenButtons) {
            sb.setVisible(false);
        }
        for(ScreenImage sc: screenImages) {
            sc.setVisible(false);
        }
    }

    // Add game Over Menu to the Controller
    private void addGameOverMenu(){
        Game.setGameOverMenu(new GameOver<>());
        Game.controller.add(Game.getGameOverMenu());
    }

    public static boolean hasSelected() {
        return selected;
    }
}
