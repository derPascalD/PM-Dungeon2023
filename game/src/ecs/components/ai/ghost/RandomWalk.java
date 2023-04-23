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

    @Override
    public void idle(Entity entity) {
        if (path == null || AITools.pathFinishedOrLeft(entity, path)) {
            path = AITools.calculatePathToRandomTileInRange(entity, 2);
        } else AITools.move(entity, path);
    }

    @Override
    public boolean isInFightMode(Entity entity) {
        return false;
    }
}
