package starter;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static logging.LoggerConfig.initBaseLogger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import configuration.Configuration;
import configuration.KeyboardConfig;
import controller.AbstractController;
import controller.SystemController;
import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.entities.Chest;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.MonsterChest;
import ecs.entities.Monsters.Demon;
import ecs.entities.Monsters.PumpkinKiller;
import ecs.entities.Monsters.Skeleton;
import ecs.entities.NPCs.Ghost;
import ecs.entities.Traps.Bananapeel;
import ecs.entities.Traps.Poisoncloud;
import ecs.items.ImplementedItems.Bag;
import ecs.items.ImplementedItems.Chestplate;
import ecs.items.ImplementedItems.Healthpot;
import ecs.items.ImplementedItems.SimpleWand;
import ecs.items.ItemData;
import ecs.items.ItemDataGenerator;
import ecs.items.ItemType;
import ecs.items.WorldItemBuilder;
import ecs.quest.DemonSlayerQuest;
import ecs.quest.HealQuest;
import ecs.quest.LevelUpQuest;
import ecs.quest.Quest;
import ecs.systems.*;
import graphic.DungeonCamera;
import graphic.IngameUI;
import graphic.Painter;
import graphic.hud.HealingBar;
import graphic.hud.PauseMenu;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import level.IOnLevelLoader;
import level.LevelAPI;
import level.elements.ILevel;
import level.elements.tile.Tile;
import level.generator.IGenerator;
import level.generator.postGeneration.WallGenerator;
import level.generator.randomwalk.RandomWalkGenerator;
import level.tools.LevelElement;
import level.tools.LevelSize;
import tools.Constants;
import tools.Point;

/** The heart of the framework. From here all strings are pulled. */
public class Game extends ScreenAdapter implements IOnLevelLoader {

    private final LevelSize LEVELSIZE = LevelSize.SMALL;
    private LevelSize LevelDepthSize = LevelSize.SMALL;

    /**
     * The batch is necessary to draw ALL the stuff. Every object that uses draw need to know the
     * batch.
     */
    protected SpriteBatch batch;

    /** Contains all Controller of the Dungeon */
    protected List<AbstractController<?>> controller;

    public static DungeonCamera camera;
    /** Draws objects */
    protected Painter painter;

    protected LevelAPI levelAPI;
    /** Generates the level */
    protected IGenerator generator;

    private boolean doSetup = true;
    private static boolean paused = false;

    /** Saves the level */
    private static int levelDepth;

    /** All entities that are currently active in the dungeon */
    private static final Set<Entity> entities = new HashSet<>();
    /** All entities to be removed from the dungeon in the next frame */
    private static final Set<Entity> entitiesToRemove = new HashSet<>();
    /** All entities to be added from the dungeon in the next frame */
    private static final Set<Entity> entitiesToAdd = new HashSet<>();

    /** List of all Systems in the ECS */
    public static SystemController systems;

    public static ILevel currentLevel;
    private static PauseMenu<Actor> pauseMenu;
    private static Entity hero;
    private Logger gameLogger;
    private Random rand = new Random();
    private IngameUI ui;
    private int questNumber;

    public static void main(String[] args) {
        // start the game
        try {
            Configuration.loadAndGetConfiguration("dungeon_config.json", KeyboardConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DesktopLauncher.run(new Game());
    }

    /**
     * Main game loop. Redraws the dungeon and calls the own implementation (beginFrame, endFrame
     * and onLevelLoad).
     *
     * @param delta Time since last loop.
     */
    @Override
    public void render(float delta) {
        if (doSetup) setup();
        batch.setProjectionMatrix(camera.combined);
        frame();
        clearScreen();
        levelAPI.update();
        controller.forEach(AbstractController::update);
        camera.update();
    }

    /** Called once at the beginning of the game. */
    protected void setup() {
        doSetup = false;
        controller = new ArrayList<>();
        setupCameras();
        painter = new Painter(batch, camera);
        generator = new RandomWalkGenerator();
        levelAPI = new LevelAPI(batch, painter, generator, this);
        initBaseLogger();
        gameLogger = Logger.getLogger(this.getClass().getName());
        systems = new SystemController();
        controller.add(systems);
        pauseMenu = new PauseMenu<>();
        controller.add(pauseMenu);

        hero = new Hero();
        createQuests();
        ui = new IngameUI<>();
        controller.add(ui);
        controller.add(new HealingBar<>());
        levelAPI = new LevelAPI(batch, painter, new WallGenerator(new RandomWalkGenerator()), this);
        levelAPI.loadLevel(LEVELSIZE);
        createSystems();
    }

    /** Called at the beginning of each frame. Before the controllers call <code>update</code>. */
    protected void frame() {
        setCameraFocus();
        manageEntitiesSets();
        getHero().ifPresent(this::loadNextLevelIfEntityIsOnEndTile);
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) togglePause();
        if (Gdx.input.isKeyJustPressed(KeyboardConfig.TOGGLE_QUESTS.get()))
            IngameUI.toggleQuestText();
        if (levelDepth == 1) {
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.ACCEPT_QUEST.get())) acceptCurrentQuest();
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.NEXT_QUESTS.get())) skipQuest();
        } else {
            IngameUI.setQuestAcceptText(false);
        }
    }

    @Override
    public void onLevelLoad() {
        HealingBar.updateHealingBar(null, false, 0);
        currentLevel = levelAPI.getCurrentLevel();
        entities.clear();
        getHero().ifPresent(this::placeOnLevelStart);

        createMonster();
        addXPToEntity();
        setupChest();


        new Poisoncloud();
        new Bananapeel();
        new Bananapeel();

        if (rand.nextBoolean()) {
            new Ghost();
        }

        createItems();
    }

    private void setupChest()
    {
        List<ItemData> items = new ArrayList<>();
        new MonsterChest(items, Game.currentLevel.getRandomTile(LevelElement.FLOOR).getCoordinate().toPoint());
    }

    private void manageEntitiesSets() {
        entities.removeAll(entitiesToRemove);
        entities.addAll(entitiesToAdd);
        for (Entity entity : entitiesToRemove) {
            gameLogger.info("Entity '" + entity.getClass().getSimpleName() + "' was deleted.");
        }
        for (Entity entity : entitiesToAdd) {
            gameLogger.info("Entity '" + entity.getClass().getSimpleName() + "' was added.");
        }
        entitiesToRemove.clear();
        entitiesToAdd.clear();
    }

    private void setCameraFocus() {
        if (getHero().isPresent()) {
            PositionComponent pc =
                    (PositionComponent)
                            getHero()
                                    .get()
                                    .getComponent(PositionComponent.class)
                                    .orElseThrow(
                                            () ->
                                                    new MissingComponentException(
                                                            "PositionComponent"));
            camera.setFocusPoint(pc.getPosition());

        } else camera.setFocusPoint(new Point(0, 0));
    }

    // Collect with the
    private void loadNextLevelIfEntityIsOnEndTile(Entity hero) {
        if (isOnEndTile(hero)) levelAPI.loadLevel(LevelDepthSize);
    }

    private boolean isOnEndTile(Entity entity) {
        PositionComponent pc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        Tile currentTile = currentLevel.getTileAt(pc.getPosition().toCoordinate());
        return currentTile.equals(currentLevel.getEndTile());
    }

    private void placeOnLevelStart(Entity hero) {
        entities.add(hero);
        PositionComponent pc =
                (PositionComponent)
                        hero.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        pc.setPosition(currentLevel.getStartTile().getCoordinate().toPoint());
    }

    /** Toggle between pause and run */
    public static void togglePause() {
        paused = !paused;
        if (systems != null) {
            systems.forEach(ECS_System::toggleRun);
        }
        if (pauseMenu != null) {
            if (paused) pauseMenu.showMenu();
            else pauseMenu.hideMenu();
        }
    }

    /**
     * English: Here different monsters are created and implemented into the level. From level 8,
     * larger levels are created. From level 50, even larger levels are created.
     */
    /**
     * German: Hier werden verschiedene Monster erzeugt und ins Level implementiert. Ab Level 8
     * werden größere Level erzeugt. Ab Level 50 werden noch größere Level erzeugt.
     */
    public void createMonster() {
        for (int i = 0; i < 1 + (levelDepth * 0.3); i++) {
            int monster = (int) (Math.random() * 3);
            if (monster == 0) new Demon(levelDepth);
            else if (monster == 1) new Skeleton(levelDepth);
            else if (monster == 2) new PumpkinKiller(levelDepth);
        }
        if (levelDepth >= 6) LevelDepthSize = LevelSize.MEDIUM;
        if (levelDepth >= 48) LevelDepthSize = LevelSize.LARGE;
        levelDepth++;


    }

    public void addXPToEntity() {
        if (Game.hero != null) {
            Hero hero1 = (Hero) Game.hero;
            hero1.getXpComponent().addXP(50);
        }
    }

    /** Creates Items in the Level depending on the levelDepth */
    public void createItems() {
        ItemDataGenerator itemdata = new ItemDataGenerator();
        for (int i = 0; i < 1 + (levelDepth * 0.3); i++) {
            if (rand.nextBoolean()) WorldItemBuilder.buildWorldItem(itemdata.generateItemData());
            else if (rand.nextInt(101) > 30 && levelDepth >= 3) new Bag(ItemType.Healing);
            else if (rand.nextBoolean()) WorldItemBuilder.buildWorldItem(itemdata.generateItemData());
            else if (rand.nextBoolean()) WorldItemBuilder.buildWorldItem(itemdata.generateItemData());
        }
    }

    // Creates all the Quests in the Dungeon with their respective name and text
    private void createQuests() {
        new LevelUpQuest("Deeper Pockets", "Reach Dungeon depth 8 to get more 3 Inventory slots");
        new HealQuest("More equals better, right?", "Use 10 Healpots for more HP");
        new DemonSlayerQuest("Bloodrush", "Kill 10 Demons to receive 'Demonslayer'");
    }

    // Ensures that quests are accepted that you have confirmed
    private void acceptCurrentQuest() {
        if (questNumber < Quest.getAllQuests().size()) {
            Quest.getAllQuests().get(questNumber).setAccepted(true);
            questNumber++;
            if (questNumber >= Quest.getAllQuests().size()) {
                IngameUI.setQuestAcceptText(false);
            } else {
                updateQuestAcceptText();
                gameLogger.info(Quest.getAllQuests().get(questNumber).getName() + " got accepted");
            }
        } else {
            IngameUI.updateQuestAcceptText("");
        }
        if (questNumber >= Quest.getAllQuests().size()) {
            IngameUI.setQuestAcceptText(false);
        }
    }

    // Ensures that once you have rejected a quest, you skip the quest and thus do not accept it.
    private void skipQuest() {
        questNumber++;
        if (questNumber < Quest.getAllQuests().size()) {
            updateQuestAcceptText();
        } else {
            IngameUI.updateQuestAcceptText("");
        }
    }

    // Ensures the correct output of the quest to be selected
    private void updateQuestAcceptText() {
        if (questNumber < Quest.getAllQuests().size()) {
            StringBuilder text = new StringBuilder();
            text.append("Accept with 'H' and skip with 'K'");
            text.append("\nQuest name: " + Quest.getAllQuests().get(questNumber).getName());
            text.append(
                    "\nQuest Description: "
                            + Quest.getAllQuests().get(questNumber).getDescription());
            IngameUI.updateQuestAcceptText(text.toString());
        }
    }

    /**
     * @return Returns the current levelDepth of the dungeon
     */
    public static int getLevelDepth() {
        return levelDepth;
    }

    /**
     * Given entity will be added to the game in the next frame
     *
     * @param entity will be added to the game next frame
     */
    public static void addEntity(Entity entity) {
        entitiesToAdd.add(entity);
    }

    /**
     * Given entity will be removed from the game in the next frame
     *
     * @param entity will be removed from the game next frame
     */
    public static void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    /**
     * @return Set with all entities currently in game
     */
    public static Set<Entity> getEntities() {
        return entities;
    }

    /**
     * @return Set with all entities that will be added to the game next frame
     */
    public static Set<Entity> getEntitiesToAdd() {
        return entitiesToAdd;
    }

    /**
     * @return Set with all entities that will be removed from the game next frame
     */
    public static Set<Entity> getEntitiesToRemove() {
        return entitiesToRemove;
    }

    /**
     * @return the player character, can be null if not initialized
     */
    public static Optional<Entity> getHero() {
        return Optional.ofNullable(hero);
    }

    /**
     * set the reference of the playable character careful: old hero will not be removed from the
     * game
     *
     * @param hero new reference of hero
     */
    public static void setHero(Entity hero) {
        Game.hero = hero;
    }

    public void setSpriteBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void setupCameras() {
        camera = new DungeonCamera(null, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.zoom = Constants.DEFAULT_ZOOM_FACTOR;

        // See also:
        // https://stackoverflow.com/questions/52011592/libgdx-set-ortho-camera
    }

    private void createSystems() {
        new VelocitySystem();
        new DrawSystem(painter);
        new PlayerSystem();
        new AISystem();
        new CollisionSystem();
        new HealthSystem();
        new XPSystem();
        new SkillSystem();
        new ProjectileSystem();
        new QuestSystem();
        new HealingSystem();
    }
}
