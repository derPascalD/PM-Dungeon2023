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

    private final int StunningStrikeCoolDown = 20;
    private final int SpeedSkillCoolDown = 20;

    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;
    private String hitAnimation = "knight/hit";


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


    private PlayableComponent playableComponent;
    private XPComponent xpComponent;
    private HealthComponent health;


    /**
     * Entity with Components
     */
    public Hero() {
        super();
        playableComponent = new PlayableComponent(this);
        new PositionComponent(this);


        setupXPComponent();

        setupVelocityComponent();
        setupAnimationComponent();
        setupHealthComponent();
        setupHitboxComponent();

        setupSkillComponent();
        setupFireballSkill();
        setupStunningStrikeSkill();


    }


    private void setupSpeedSkill() {
        skillComponent.addSkill(
            secondSkill =
                new Skill(
                    new SpeedSkill(xSpeed, ySpeed, 0.3F, 0.3F, 4), SpeedSkillCoolDown));
        playableComponent.setSkillSlot1(secondSkill);
        System.out.println("SpeedSKill unlocked");

    }

    private void setupStunningStrikeSkill() {
        skillComponent.addSkill(
            thirdSkill =
                new Skill(
                    new StunningStrikeSkill(4), StunningStrikeCoolDown));
        playableComponent.setSkillSlot2(thirdSkill);
        System.out.println("StunningStrikeSkill unlocked");
    }

    @Override
    public void onLevelUp(long nexLevel) {
        System.out.println("Level: " + nexLevel);
        System.out.println("Punkte: " + xpComponent.getCurrentXP());
        System.out.println("Punkte noch zum nächsten Levelaufstieg: " + xpComponent.getXPToNextLevel());
        if (nexLevel == 2) setupSpeedSkill();
        if (nexLevel == 3) setupStunningStrikeSkill();


    }

    private void setupSkillComponent() {
        skillComponent = new SkillComponent(this);


    }

    private void setupXPComponent() {
        xpComponent = new XPComponent(this, this);
    }

    private void setupHealthComponent() {
        health = new HealthComponent(this, lifePoints, this, hitAnimation(), hitAnimation());
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

    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> System.out.println("Hero collide"),
            (you, other, direction) -> System.out.println("Hero not collide")
        );
    }


    private void setupFireballSkill() {
        firstSkill =
            new Skill(
                new FireballSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);
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


    public Skill getSecondSkill() {
        return secondSkill;
    }

    public Skill getThirdSkill() {
        return thirdSkill;
    }

    public SkillComponent getSkillComponent() {
        return skillComponent;
    }

    public XPComponent getXpComponent() {
        return xpComponent;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }
}
