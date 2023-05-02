package ecs.components.skill;

import ecs.entities.Entity;

public abstract class MagicSkills implements ISkillFunction {


    protected int skillCosts;

    protected float skillDuration;

    public MagicSkills(int skillCosts, float skillDuration) {
        this.skillCosts = skillCosts;
        this.skillDuration = skillDuration;


    }


    @Override
    public abstract void execute(Entity entity);
}
