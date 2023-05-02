package ecs.components.skill;

import ecs.entities.Entity;

public abstract class MagicSkills implements ISkillFunction{






    @Override
    public abstract void execute(Entity entity);
}
