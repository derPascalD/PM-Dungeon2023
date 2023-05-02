package ecs.components.skill.magic;

import ecs.components.skill.ISkillFunction;
import ecs.entities.Entity;

public abstract class MagicSkills implements ISkillFunction {


    protected int skilHealthlCosts;

    protected float skillDuration;

    public MagicSkills(int skilHealthlCosts, float skillDuration) {
        this.skilHealthlCosts = skilHealthlCosts;
        this.skillDuration = skillDuration;


    }


    @Override
    public abstract void execute(Entity entity);
}
