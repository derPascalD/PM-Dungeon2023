package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.components.skill.magic.SpeedSkill;
import ecs.components.skill.magic.StunningStrikeSkill;
import ecs.components.xp.ILevelUp;
import ecs.components.xp.XPComponent;
import graphic.Animation;


/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
public class Hero extends Entity implements IOnDeathFunction, ILevelUp {


    private final int fireballCoolDown = 5;

    private final int StunningStrikeCoolDown = 10;
    private final int SpeedSkillCoolDown = 10;
    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;
    private String hitAnimation = "knight/hit";
    private String attackAnimation = "knight/attack";


    // Life points from Hero
    private int lifePoints = 20;

    private String pathToIdleLeft = "knight/idleLeft";
    private String pathToIdleRight = "knight/idleRight";
    private String pathToRunLeft = "knight/runLeft";
    private String pathToRunRight = "knight/runRight";

    // Skills from Hero
    private Skill firstSkill;
    private Skill secondSkill;
    private Skill thirdSkill;
    private SkillComponent skillComponent;

    protected HealthComponent health;


    /**
     * Entity with Components
     */
    public Hero() {
        super();
        this.health = new HealthComponent(this, lifePoints, this, hitAnimation(), attackAnimation());
        PlayableComponent pc = new PlayableComponent(this);
        this.skillComponent = new SkillComponent(this);
        new PositionComponent(this);


        setupFireballSkill();
        setupStunningStrikeSkill();
        setupSpeedSkill();
        setupVelocityComponent();
        setupAnimationComponent();
        setupHealthComponent();
        setupHitboxComponent();
        setupFireballSkill();
        setupStunningStrikeSkill();
        setupXPComponent();
        setupHealthComponent();


        pc.setSkillSlot1(secondSkill);
        pc.setSkillSlot2(thirdSkill);


    }


    private void setupXPComponent() {
        new XPComponent(this, this);
    }



    private void setupSpeedSkill() {
        skillComponent.addSkill(
            secondSkill =
                new Skill(
                    new SpeedSkill(1, 1, 4), SpeedSkillCoolDown));


    }

    private void setupStunningStrikeSkill() {
        skillComponent.addSkill(
            thirdSkill =
                new Skill(
                    new StunningStrikeSkill(4), StunningStrikeCoolDown));


    }

    @Override
    public void onLevelUp(long nexLevel) {
        //TODO:
    }
    private void setupHealthComponent() {
        Animation hit = AnimationBuilder.buildAnimation("traps/Wolke/clouds");
        new HealthComponent(this, 100, this::onDeath, hit, hit);
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
            (you, other, direction) -> System.out.println("Hero collide"),
            (you, other, direction) -> System.out.println("Hero not collide")

        );
    }

    public Animation attackAnimation() {
        return AnimationBuilder.buildAnimation(attackAnimation);
    }

    public Animation hitAnimation() {
        return AnimationBuilder.buildAnimation(hitAnimation);
    }


    /*
     As soon as the entity dies, the content of the function is executed.
    */
    @Override
    public void onDeath(Entity entity) {
        System.out.println("Hero is dead!");
        System.exit(0);
    }

    /**
     * @return Return the current data path of the Hero Animation left
     */
    public String getPathToIdleLeft() {
        return pathToIdleLeft;
    }

    /**
     * @return Return the current data path of the Hero Animation right
     */
    public String getPathToIdleRight() {
        return pathToIdleRight;
    }

    /**
     * @return Return the current data path of the Hero Animation run left
     */
    public String getPathToRunLeft() {
        return pathToRunLeft;
    }

    /**
     * @return Return the current data path of the Hero Animation run right
     */
    public String getPathToRunRight() {
        return pathToRunRight;
    }



}
