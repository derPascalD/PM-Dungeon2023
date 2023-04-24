package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.IOnDeathFunction;
import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.GoToHero;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.collision.ICollide;
import level.elements.tile.Tile;

public class Skeleton extends Monster implements IOnDeathFunction, ICollide {
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
    public Skeleton(int levelDepth) {
        this.attackDamage = 1;
        this.xSpeed = 0.04f;
        this.ySpeed = 0.04f;
        this.lifePoints = 6;
        this.diagonal = false;
        this.pathToIdleLeft = "monster/skeleton/idleLeft";
        this.pathToIdleRight = "monster/skeleton/idleRight";
        this.pathToRunLeft = "monster/skeleton/runLeft";
        this.pathToRunRight = "monster/skeleton/runRight";

        new PositionComponent(this);
        new HitboxComponent(this, this::onCollision, this::onCollisionLeave);
        new AIComponent(this, new CollideAI(0f), new GoToHero(2), new RangeTransition(1f));
        this.hit = AnimationBuilder.buildAnimation("monster/skeleton/idleLeft");
        this.die = AnimationBuilder.buildAnimation("monster/skeleton/idleLeft");

        health = new HealthComponent(this,
            8,
            this,
            hit,
            die);
        setupVelocityComponent();
        setupAnimationComponent();
        this.lifePoints += levelDepth * 0.5;
        this.attackDamage += levelDepth* 0.3;
        this.xSpeed += levelDepth*0.005;
        this.ySpeed += levelDepth*0.005;

        System.out.println(this.getClass().getName() + " create with: " + this.lifePoints+ " Healthpoints.");
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
    }


    @Override
    public void setLifePoints(int lifePoints) {
        super.setLifePoints(lifePoints);
    }

    @Override
    public int getLifePoints() {
        return super.getLifePoints();
    }

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
