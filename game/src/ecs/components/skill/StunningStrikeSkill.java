package ecs.components.skill;

import ecs.entities.Entity;

public class StunningStrikeSkill extends MagicSkills {

    protected boolean isSkillActive;
    protected float radius;

    public StunningStrikeSkill(int skillDuration) {
        super(1,skillDuration);

    }

    @Override
    public void execute(Entity entity) {

    }
}
