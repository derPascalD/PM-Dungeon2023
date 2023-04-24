package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import tools.Constants;

public class GoToHero implements IIdleAI{

    private GraphPath<Tile> path;
    private final int breakTime;
    private int currentBreak = 0;

    /**
     * English:
     * This allows certain entities to track the Hero.
     * The entity always looks for the last location of the Hero and goes there.
     *
     * @param breakTimeInSeconds how long to wait (in seconds) before searching a new goal
     */
    /**
     * German:
     * Damit können bestimmte Entities den Hero verfolgen. Die Entity sucht sich immer den letzten Ort des Heros
     * und geht dorthin.
     *
     * @param breakTimeInSeconds wie lange (in Sekunden) gewartet werden soll, bevor ein neues Ziel gesucht wird
     */
    public GoToHero(int breakTimeInSeconds) {

        this.breakTime = breakTimeInSeconds * Constants.FRAME_RATE;
    }

    @Override
    public void idle(Entity entity) {
        if (path == null || AITools.pathFinished(entity, path)) {
            if (currentBreak >= breakTime) {
                currentBreak = 0;
                path = AITools.calculatePathToHero(entity);
            }
            currentBreak++;

        } else AITools.move(entity, path);
    }
}
