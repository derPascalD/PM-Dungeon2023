package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.IOnDeathFunction;
import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.collision.ICollide;
import level.elements.tile.Tile;

public class PumpkinKiller extends Monster implements IOnDeathFunction, ICollide {
    /**
     * English:
     * Entity with Components.
     * Depending on the level depth, more monsters are implemented.
     * The monsters, depending on the level depth, have more life, give more damage and higher speeds.
     */
    /**
     * German:
     * Entity mit Komponenten.
     * Je nach Level Tiefe werden mehr Monster implementiert.
     * Die Monster haben je nach Level Tiefe mehr Leben, mehr Schaden geben und höhere Geschwindigkeiten.
     */
    public PumpkinKiller(int levelDepth) {
        this.attackDamage = 2;
        this.lifePoints = 10;
        this.xSpeed = 0.1f;
        this.ySpeed = 0.1f;
        this.diagonal = false;
        this.pathToIdleLeft = "monster/pumpkinKiller/idleLeft";
        this.pathToIdleRight = "monster/pumpkinKiller/idleRight";
        this.pathToRunLeft = "monster/pumpkinKiller/runLeft";
        this.pathToRunRight = "monster/pumpkinKiller/runRight";
        new HitboxComponent(this, this::onCollision, this::onCollisionLeave);
        new PositionComponent(this);
        new HealthComponent(this);


        new AIComponent(this, new CollideAI(0f), new PatrouilleWalk(4f, 4,
            2000, PatrouilleWalk.MODE.RANDOM), new RangeTransition(2f));


        this.hit = AnimationBuilder.buildAnimation("monster/pumpkinKiller/idleLeft");
        this.die = AnimationBuilder.buildAnimation("monster/pumpkinKiller/idleLeft");
        health = new HealthComponent(this, 8, this, hit, die);

        setupVelocityComponent();
        setupAnimationComponent();

        this.lifePoints += levelDepth * 0.5;
        this.attackDamage += levelDepth* 0.3;
        this.xSpeed += levelDepth*0.015;
        this.ySpeed += levelDepth*0.015;

        System.out.println(this.getClass().getName() + " create with: " + this.lifePoints+ " Healthpoints.");
        System.out.println(this.getClass().getName() + " create with: " + this.attackDamage+ " AttackDamage.");
        System.out.println(this.getClass().getName() + " " + this.xSpeed + " xSpeed.");
        System.out.println(this.getClass().getName() + " " + this.ySpeed + " ySpeed.");

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
    private void onCollisionLeave(Entity entity, Entity entity1, Tile.Direction direction) {
    }

    /*
    English:
    The function is called as soon as different entities collide with each other.
    Then certain instructions can be executed.
    */
    /*
    German:
    Die Funktion wird aufgerufen, sobald unterschiedliche Entities miteinander kollidieren.
    Da können dann bestimmte Anweisungen ausgeführt werden.
    */
    public void onCollision(Entity entity, Entity entity1, Tile.Direction direction) {
    }

    /*
    As soon as the entity dies, the content of the function is executed.
     */
    @Override
    public void onDeath(Entity entity) {
        System.out.println("PumpkinKiller is die");
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }


    public int getLifePoints() {return this.lifePoints;}

    @Override
    public float getxSpeed() {
        return super.getxSpeed();
    }

    @Override
    public void setxSpeed(float xSpeed) {
        super.setxSpeed(xSpeed);
    }

    @Override
    public float getySpeed() {
        return super.getySpeed();
    }

    @Override
    public void setySpeed(float ySpeed) {
        super.setySpeed(ySpeed);
    }

    @Override
    public int getAttackDamage() {
        return super.getAttackDamage();
    }

    @Override
    public void setAttackDamage(int attackDamage) {
        super.setAttackDamage(attackDamage);
    }


}
