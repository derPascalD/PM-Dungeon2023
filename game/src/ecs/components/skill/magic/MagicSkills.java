package ecs.components.skill.magic;

import ecs.components.skill.ISkillFunction;
import ecs.entities.Entity;

public abstract class MagicSkills implements ISkillFunction {

    protected int skillHealthCosts;
    protected float skillDuration;

    /**
     * @param skillHealthCosts Capability cost
     * @param skillDuration Ability duration
     */
    public MagicSkills(int skillHealthCosts, float skillDuration) {
        this.skillHealthCosts = skillHealthCosts;
        this.skillDuration = skillDuration;
    }

    /**
     * @param entity which uses the skill
     */
    @Override
    public abstract void execute(Entity entity);
}
