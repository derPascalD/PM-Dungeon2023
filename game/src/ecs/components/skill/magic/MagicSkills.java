package ecs.components.skill.magic;

import ecs.components.skill.ISkillFunction;
import ecs.entities.Entity;

public abstract class MagicSkills implements ISkillFunction {


    protected int skillHealthCosts;

    protected float skillDuration;

    public MagicSkills(int skillHealthCosts, float skillDuration) {
        this.skillHealthCosts = skillHealthCosts;
        this.skillDuration = skillDuration;


    }


    @Override
    public abstract void execute(Entity entity);




}
