package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import graphic.Animation;


/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
public class Hero extends Entity implements IOnDeathFunction {

    // Neu Implementiert
    private final int fireballCoolDown = 5;
    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;
    private String hitAnimation = "knight/hit";
    private String attackAnimation = "knight/attack";

    private HealthComponent health;

    private int lifePoints = 20;


    // War vorher da
    private String pathToIdleLeft = "knight/idleLeft";
    private String pathToIdleRight = "knight/idleRight";
    private String pathToRunLeft = "knight/runLeft";
    private String pathToRunRight = "knight/runRight";
    private Skill firstSkill;




    /**
     * Entity with Components
     */
    public Hero() {
        super();
        health =new HealthComponent(this,lifePoints, this,hitAnimation(),attackAnimation());
        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupFireballSkill();
        pc.setSkillSlot1(firstSkill);

    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupFireballSkill() {
        firstSkill =
            new Skill(
                new FireballSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> System.out.println("Hero Kollidiert"),
            (you, other, direction) -> System.out.println("Hero ausser gefahr")

        );
    }

    public Animation attackAnimation() {
        return AnimationBuilder.buildAnimation(attackAnimation);
    }
    public Animation hitAnimation(){
       return AnimationBuilder.buildAnimation(hitAnimation);
    }

    @Override
    public void onDeath(Entity entity) {
        System.out.println("Hero tot!");

    }
}
