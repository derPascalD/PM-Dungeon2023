package ecs.components.ai.ghost;

import ecs.components.ai.AITools;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.transition.ITransition;
import ecs.entities.Entity;

public class FollowHeroAI implements ITransition, IIdleAI {

    @Override
    public boolean isInFightMode(Entity entity) {
        return false;
    }

    @Override
    public void idle(Entity entity) {
        AITools.move(entity, AITools.calculatePathToHero(entity));
    }
}
