package ecs.components.skill.magic;

import ecs.entities.Entity;

public class SpeedSkill extends MagicSkills {

    protected float xMoreSpeed;
    protected float yMoreSpeed;

    public SpeedSkill(float xMoreSpeed, float yMoreSpeed, int skillDuration) {
        super(1, skillDuration);
        this.xMoreSpeed = xMoreSpeed;
        this.yMoreSpeed = yMoreSpeed;
    }

    @Override
    public void execute(Entity entity) {

    }
}
