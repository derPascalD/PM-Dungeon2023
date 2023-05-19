package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.collision.ICollide;
import ecs.components.skill.*;
import ecs.components.skill.magic.SpeedSkill;
import ecs.components.skill.magic.StunningStrikeSkill;
import ecs.components.xp.ILevelUp;
import ecs.components.xp.XPComponent;
import graphic.Animation;
import graphic.IngameUI;
import java.util.ArrayList;
import level.elements.tile.Tile;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
public class Hero extends Entity implements IOnDeathFunction, ILevelUp, ICollide {

    private final int fireballCoolDown = 2;
    private final int StunningStrikeCoolDown = 3;
    private final int SpeedSkillCoolDown = 20;
    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;
    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";
    private String hitAnimation = "knight/hit/knight_m_hit_anim_f0.png";
    private Skill firstSkill;
    private Skill secondSkill;
    private Skill thirdSkill;
    private Skill combatSkill;

    private ArrayList<Entity> deadMonsters;

    protected InventoryComponent inventory;
    private SkillComponent skillComponent;
    private final PlayableComponent playableComponent;
    private XPComponent xpComponent;

    private HealthComponent healthComponent;

    /** Entity with Components */
    public Hero() {
        super();
        playableComponent = new PlayableComponent(this);
        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHealthComponent();
        setupHitboxComponent();
        setupSkillComponent();
        setupXPComponent();
        setupMeleeSkill();
        setupInventoryComponent();
        setupDamageComponent();
    }

    /*
    Adds the new Speed skill to allow the Hero to run faster for a short time.
     */
    private void setupSpeedSkill() {
        skillComponent.addSkill(
                secondSkill =
                        new Skill(
                                new SpeedSkill(xSpeed, ySpeed, 0.3F, 0.3F, 4), SpeedSkillCoolDown));
        playableComponent.setSkillSlot2(secondSkill);
    }

    /*
    Adds the new StunningStrike skill to allow the Hero to run faster for a short time.
     */
    private void setupStunningStrikeSkill() {
        skillComponent.addSkill(
                thirdSkill = new Skill(new StunningStrikeSkill(4), StunningStrikeCoolDown));
        playableComponent.setSkillSlot3(thirdSkill);
    }

    /*
    Adds the new Fireball skill to allow the Hero to run faster for a short time.
     */
    private void setupFireballSkill() {
        skillComponent.addSkill(
                firstSkill =
                        new Skill(
                                new FireballSkill(SkillTools::getCursorPositionAsPoint),
                                fireballCoolDown));
        playableComponent.setSkillSlot1(firstSkill);
    }

    /*
    Adds the new Melee skill to allow the Hero to run faster for a short time.
     */
    private void setupMeleeSkill() {
        skillComponent.addSkill(
                combatSkill =
                        new Skill(
                                new Sword(
                                        1,
                                        "character/knight/attackLeft/",
                                        "character/knight/attackRight/"),
                                1F));
        playableComponent.setCombatSkill(combatSkill);
    }

    /**
     * Here abilities are unlocked, depending on the level of the hero
     *
     * @param nextLevel is the new level of the entity
     */
    @Override
    public void onLevelUp(long nextLevel) {
        if (nextLevel == 1) {
            setupFireballSkill();
            IngameUI.updateSkillsBar("Fireball", "-", "-");
        }
        if (nextLevel == 2) {
            setupSpeedSkill();
            IngameUI.updateSkillsBar("Fireball", "More Speed", "-");
        }
        if (nextLevel == 3) {
            setupStunningStrikeSkill();
            IngameUI.updateSkillsBar("Fireball", "More Speed", "StunningStrike");
        }
    }

    private void setupSkillComponent() {
        skillComponent = new SkillComponent(this);
    }

    private void setupXPComponent() {
        xpComponent = new XPComponent(this, this);
    }

    private void setupHealthComponent() {
        healthComponent = new HealthComponent(this, 100, this, hitAnimation(), null);
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
        new HitboxComponent(this, this, this::onCollisionLeave);
    }

    private void setupInventoryComponent() {
        inventory = new InventoryComponent(this, 5);
    }

    private void setupDamageComponent() {
        new DamageComponent(this);
    }

    /**
     * @return Return the Hit Animation from the Hero
     */
    public Animation hitAnimation() {
        return AnimationBuilder.buildAnimation(hitAnimation);
    }

    /** As soon as the entity dies, the content of the function is executed. */
    @Override
    public void onDeath(Entity entity) {}

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

    /**
     * @return Return the Second Skill
     */
    public Skill getSecondSkill() {
        return secondSkill;
    }

    /**
     * @return Return the Third Skill
     */
    public Skill getThirdSkill() {
        return thirdSkill;
    }

    /**
     * @return Return the SkillComponent from the Hero
     */
    public SkillComponent getSkillComponent() {
        return skillComponent;
    }

    public HealthComponent getHealthComponent() {
        return healthComponent;
    }

    /**
     * @return Return the XpComponent from the Hero
     */
    public XPComponent getXpComponent() {
        return xpComponent;
    }

    /**
     * @return Return the Original xSpeed from Hero
     */
    public float getxSpeed() {
        return xSpeed;
    }

    /**
     * @return Return the Original ySpeed from Hero
     */
    public float getySpeed() {
        return ySpeed;
    }

    public ArrayList<Entity> getDeadMonsters() {
        return deadMonsters;
    }

    /*
    English:
    The function is called as soon as different entities no longer collide with each other.
    Then certain instructions can be executed.
    */
    /*
    German:
    Die Funktion wird aufgerufen, sobald unterschiedliche Entities nicht mehr miteinander kollidieren.
    Da können dann bestimmte Anweisungen ausgeführt werden.
    */
    private void onCollisionLeave(Entity a, Entity b, Tile.Direction direction) {}

    /**
     * English: The function is called as soon as different entities collide with each other. Then
     * certain instructions can be executed.
     */
    /**
     * German: Die Funktion wird aufgerufen, sobald unterschiedliche Entities miteinander
     * kollidieren. Da können dann bestimmte Anweisungen ausgeführt werden.
     */
    public void onCollision(Entity a, Entity b, Tile.Direction direction) {}
}
