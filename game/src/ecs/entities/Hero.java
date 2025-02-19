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
import ecs.damage.Damage;
import ecs.damage.DamageType;
import graphic.Animation;
import graphic.IngameUI;
import java.util.ArrayList;
import level.elements.tile.Tile;
import tools.Point;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes.
 */
public class Hero extends Entity implements IOnDeathFunction, ILevelUp, ICollide {

    private int fireballCoolDown = 1;
    private int StunningStrikeCoolDown = 3;
    private int SpeedSkillCoolDown = 20;

    private final int grenadeLauncherCoolDown = 1;
    private final int NinjabladeCoolDown = 0;

    // Original Speed from Hero

    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;
    private String pathToIdleLeft = "knight/idleCombatLeft";
    private String pathToIdleRight = "knight/idleCombatRight";
    private String pathToRunLeft = "knight/runCombatLeft";
    private String pathToRunRight = "knight/runCombatRight";
    private String hitAnimation = "knight/hit/knight_m_hit_anim_f0.png";

    // Skills from Hero
    private Skill firstSkill;
    private Skill secondSkill;
    private Skill thirdSkill;
    private Skill combatSkill;
    private boolean equipWeapon = false;
    private final ArrayList<Entity> killedMonsters;

    private Skill fourthSkill;
    private Skill fifthSkill;

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
        new HealingComponent(this, 5, 1, 1);
        killedMonsters = new ArrayList<>();
        setupVelocityComponent();
        setupAnimationComponent();
        setupDamageComponent();
        setupHealthComponent();
        setupHitboxComponent();
        setupSkillComponent();
        setupXPComponent();
        setupMeleeSkill();
        setupInventoryComponent();
        setupNinjaBlade();
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
            setupGrenadeLauncherSkill();
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

    private void setupGrenadeLauncherSkill() {
        skillComponent.addSkill(
                fourthSkill =
                        new Skill(
                                new GrenadeLauncher(
                                        5,
                                        true,
                                        "items/grenade/grenade.png",
                                        0.6f,
                                        new Damage(2, DamageType.FIRE, null),
                                        new Point(1f, 1f),
                                        SkillTools::getCursorPositionAsPoint,
                                        3f),
                                grenadeLauncherCoolDown));
        playableComponent.setSkillSlot4(fourthSkill);
    }

    private void setupSkillComponent() {
        skillComponent = new SkillComponent(this);
    }

    private void setupXPComponent() {
        xpComponent = new XPComponent(this, this);
    }

    private void setupHealthComponent() {
        Animation hit = AnimationBuilder.buildAnimation(hitAnimation);
        healthComponent = new HealthComponent(this, 11, this, hitAnimation(), hit);
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

    private void setupNinjaBlade() {
        skillComponent.addSkill(
                fifthSkill =
                        new Skill(
                                new NinjaBlade(
                                        10,
                                        false,
                                        "skills/ninjablade/ninja_blade_left",
                                        0.25f,
                                        new Damage(2, DamageType.PHYSICAL, null),
                                        new Point(0.5f, 0.5f),
                                        SkillTools::getCursorPositionAsPoint,
                                        5f),
                                NinjabladeCoolDown));
        playableComponent.setSkillSlot5(fifthSkill);
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

    /**
     * @return Is weapon equiq or not
     */
    public boolean isEquipWeapon() {
        return equipWeapon;
    }

    /**
     * @param equipWeapon Set Weapon Equiq or not
     */
    public void setEquipWeapon(boolean equipWeapon) {
        this.equipWeapon = equipWeapon;
    }

    /**
     * @return List with killed Monsters
     */
    public ArrayList<Entity> getKilledMonsters() {
        return killedMonsters;
    }

    /**
     * @param killedMonster add killed Monsters to the List
     */
    public void addKilledMonsters(Entity killedMonster) {
        killedMonsters.add(killedMonster);
    }

    /*
    The function is called as soon as different entities no longer collide with each other.
    Then certain instructions can be executed.
    */
    private void onCollisionLeave(Entity a, Entity b, Tile.Direction direction) {}

    public void onCollision(Entity a, Entity b, Tile.Direction direction) {}

    public void setPathToIdleLeft(String pathToIdleLeft) {
        this.pathToIdleLeft = pathToIdleLeft;
    }

    public void setPathToIdleRight(String pathToIdleRight) {
        this.pathToIdleRight = pathToIdleRight;
    }

    public void setPathToRunLeft(String pathToRunLeft) {
        this.pathToRunLeft = pathToRunLeft;
    }

    public void setPathToRunRight(String pathToRunRight) {
        this.pathToRunRight = pathToRunRight;
    }
}
