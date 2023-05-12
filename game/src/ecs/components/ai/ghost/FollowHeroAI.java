package ecs.components.ai.ghost;

import ecs.components.ai.AITools;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.transition.ITransition;
import ecs.entities.Entity;

public class FollowHeroAI implements ITransition, IIdleAI {
    /**
     * Is always false because the Entity just always idles around when following the Hero
     *
     * @param entity associated entity
     * @return false
     */
    @Override
    public boolean isInFightMode(Entity entity) {
        return false;
    }

    /**
     * Entity follows the Hero
     *
     * @param entity associated entity
     */
    @Override
    public void idle(Entity entity) {
        AITools.move(entity, AITools.calculatePathToHero(entity));
    }
}
