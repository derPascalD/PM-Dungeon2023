package ecs.components.skill.magic;

import ecs.components.Component;
import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;

import java.util.Date;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;


public class SpeedSkill extends MagicSkills {

    protected float orignalXSpeed;
    protected float orignalYSpeed;

    protected float xMoreSpeed;
    protected float yMoreSpeed;


    public SpeedSkill(float orignalXSpeed, float orignalYSpeed,float xMoreSpeed, float yMoreSpeed, int skillDuration) {
        super(1, skillDuration);
        this.xMoreSpeed = xMoreSpeed;
        this.yMoreSpeed = yMoreSpeed;
        this.orignalXSpeed = orignalXSpeed;
        this.orignalYSpeed = orignalYSpeed;

    }

    @Override
    public void execute(Entity entity) {

        HealthComponent entityHP = (HealthComponent) entity.getComponent(HealthComponent.class)
            .orElseThrow(() -> new IllegalStateException("Entity does not have a HealthComponent"));


        VelocityComponent entityXY = (VelocityComponent) entity.getComponent(VelocityComponent.class)
            .orElseThrow(() -> new IllegalStateException("Entity does not have a VelocityComponent"));


        entityHP.setCurrentHealthpoints(entityHP.getCurrentHealthpoints() - skillHealthCosts);
        System.out.println("New HP: " + entityHP.getCurrentHealthpoints());

        entityXY.setXVelocity(entityXY.getXVelocity() + xMoreSpeed);
        entityXY.setYVelocity(entityXY.getYVelocity() + yMoreSpeed);
        System.out.println("New Speed: " + entityXY.getXVelocity());

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {


            public void run() {

                entityXY.setXVelocity(orignalXSpeed);
                entityXY.setYVelocity(orignalYSpeed);
                System.out.println("Speed is now " + entityXY.getXVelocity());
            }
        }, (long) skillDuration*1000);





    }




}
