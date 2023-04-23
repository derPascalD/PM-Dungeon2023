package ecs.components.ai.fight;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.transition.ITransition;
import ecs.entities.Entity;
import level.elements.tile.Tile;

public class FollowHeroAI implements ITransition, IFightAI {

    @Override
    public boolean isInFightMode(Entity entity) {
        return true;
    }

    @Override
    public void fight(Entity entity) {
        AITools.move(entity, AITools.calculatePathToHero(entity));
    }
}
