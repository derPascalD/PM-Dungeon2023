package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.components.ai.transition.ITransition;
import ecs.entities.Entity;
import level.elements.tile.Tile;

public class RandomWalk implements IIdleAI, ITransition {
    @Override
    public void idle(Entity entity) {
        GraphPath<Tile> path = null;
        AITools.move(entity,AITools.calculatePathToRandomTileInRange(entity,5));
    }

    @Override
    public boolean isInFightMode(Entity entity) {
        return false;
    }
}
