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


    private final int fireballCoolDown = 5;
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
    private Skill firstSkill;

    protected HealthComponent health;
    protected InventoryComponent inventory;

    /**
     * Entity with Components
     */
    public Hero() {
        super();
        //this.health =  new HealthComponent(this,lifePoints, this,hitAnimation(),attackAnimation());
        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHealthComponent();
        setupHitboxComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupFireballSkill();
        pc.setSkillSlot1(firstSkill);



        setupHealthComponent();
        setupInventoryComponent();
        setupDamageComponent();
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

    private void setupInventoryComponent() {
        inventory = new InventoryComponent(this,5);
    }

    private void setupDamageComponent() {
        new DamageComponent(this);
    }

    public Animation attackAnimation() {
        return AnimationBuilder.buildAnimation(attackAnimation);
    }
    public Animation hitAnimation(){
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
     *
     * @return Return the current data path of the Hero Animation left
     */
    public String getPathToIdleLeft() {
        return pathToIdleLeft;
    }
    /**
     *
     * @return Return the current data path of the Hero Animation right
     */
    public String getPathToIdleRight() {
        return pathToIdleRight;
    }
    /**
     *
     * @return Return the current data path of the Hero Animation run left
     */
    public String getPathToRunLeft() {
        return pathToRunLeft;
    }
    /**
     *
     * @return Return the current data path of the Hero Animation run right
     */
    public String getPathToRunRight() {
        return pathToRunRight;
    }


    private void setupHealthComponent()
    {
        Animation hit = AnimationBuilder.buildAnimation("traps/Wolke/clouds");
        new HealthComponent(this, 100, this::onDeath ,hit,hit);
    }




}
