package ecs.components.ai.ghost;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.transition.ITransition;
import ecs.entities.Entity;
import level.elements.tile.Tile;

public class RandomWalk implements IIdleAI, ITransition {

    private GraphPath<Tile> path;
    private int breakTime = 0;
    private int currentBreak = 0;

    /**
     * Entity walks around the level in random paths
     * @param entity associated entity
     */
    @Override
    public void idle(Entity entity) {
        if (path == null || AITools.pathFinishedOrLeft(entity, path)) {
            path = AITools.calculatePathToRandomTileInRange(entity, 2);
        } else AITools.move(entity, path);
    }

    /**
     * Is always false because the Entity just always idles around when following the Hero
     * @param entity associated entity
     * @return false
     */
    @Override
    public boolean isInFightMode(Entity entity) {
        return false;
    }
}
