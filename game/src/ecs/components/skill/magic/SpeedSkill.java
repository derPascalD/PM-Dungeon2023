package ecs.components.skill.magic;

import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import java.util.Timer;
import java.util.TimerTask;


public class SpeedSkill extends MagicSkills {

    protected float originalXSpeed;
    protected float originalYSpeed;
    protected float xMoreSpeed;
    protected float yMoreSpeed;


    public SpeedSkill(float originalXSpeed, float originalYSpeed,float xMoreSpeed, float yMoreSpeed, int skillDuration) {
        super(1, skillDuration);
        this.xMoreSpeed = xMoreSpeed;
        this.yMoreSpeed = yMoreSpeed;
        this.originalXSpeed = originalXSpeed;
        this.originalYSpeed = originalYSpeed;
    }

    @Override
    public void execute(Entity entity) {

        HealthComponent entityHP = (HealthComponent) entity.getComponent(HealthComponent.class)
            .orElseThrow(() -> new IllegalStateException("Entity does not have a HealthComponent"));

        VelocityComponent entityXY = (VelocityComponent) entity.getComponent(VelocityComponent.class)
            .orElseThrow(() -> new IllegalStateException("Entity does not have a VelocityComponent"));

        entityHP.setCurrentHealthpoints(entityHP.getCurrentHealthpoints() - skillHealthCosts);
        entityXY.setXVelocity(entityXY.getXVelocity() + xMoreSpeed);
        entityXY.setYVelocity(entityXY.getYVelocity() + yMoreSpeed);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {

                entityXY.setXVelocity(originalXSpeed);
                entityXY.setYVelocity(originalYSpeed);

            }
        }, (long) skillDuration*1000);
    }
}
