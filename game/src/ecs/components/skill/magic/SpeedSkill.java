package ecs.components.skill.magic;

import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import ecs.entities.Traps.Bananenschale;

import java.util.Timer;
import java.util.TimerTask;


public class SpeedSkill extends MagicSkills {

    protected float originalXSpeed;
    protected float originalYSpeed;
    protected float xMoreSpeed;
    protected float yMoreSpeed;

    /**
     *
     * @param originalXSpeed Original xSpeed from Entity
     * @param originalYSpeed Original xSpeed from Entity
     * @param xMoreSpeed additional x speed
     * @param yMoreSpeed additional y speed
     * @param skillDuration Ability duration
     */
    public SpeedSkill(float originalXSpeed, float originalYSpeed,float xMoreSpeed, float yMoreSpeed, int skillDuration) {
        super(1, skillDuration);
        this.xMoreSpeed = xMoreSpeed;
        this.yMoreSpeed = yMoreSpeed;
        this.originalXSpeed = originalXSpeed;
        this.originalYSpeed = originalYSpeed;
    }

    /**
     * When activating the skill, the entity runs faster in the dungeon for
     * a certain time and then again normally
     *
     * @param entity which uses the skill
     */
    @Override
    public void execute(Entity entity) {



        HealthComponent entityHP = (HealthComponent) entity.getComponent(HealthComponent.class)
            .orElseThrow(() -> new IllegalStateException("Entity does not have a HealthComponent"));

        VelocityComponent entityXY = (VelocityComponent) entity.getComponent(VelocityComponent.class)
            .orElseThrow(() -> new IllegalStateException("Entity does not have a VelocityComponent"));

        entityHP.setCurrentHealthpoints(entityHP.getCurrentHealthpoints() - skillHealthCosts);
        entityXY.setXVelocity(entityXY.getXVelocity() + xMoreSpeed);
        entityXY.setYVelocity(entityXY.getYVelocity() + yMoreSpeed);
        System.out.println("SpeedSkill active");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {


            public void run() {
                if(entityXY.getXVelocity() == (originalXSpeed + xMoreSpeed) ){
                    entityXY.setXVelocity(originalXSpeed);
                    entityXY.setYVelocity(originalYSpeed);
                }
                System.out.println("SpeedSkill ends");

            }
        }, (long) skillDuration*1000);
    }
}
