package ecs.entities.Monsters;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CombatAI;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.MonsterCombat;
import ecs.components.skill.Skill;
import ecs.components.skill.SkillComponent;
import ecs.entities.Entity;
import level.elements.tile.Tile;

public class Demon extends Monster {

    private transient Skill combatFight;
    private transient SkillComponent skillComponent;

    /**
     * English: Entity with Components. Depending on the level depth, more monsters are implemented.
     * The monsters, depending on the level depth, have more life, give more damage and higher
     * speeds.
     */
    /**
     * German: Entity mit Komponenten. Je nach Level Tiefe werden mehr Monster implementiert. Die
     * Monster haben je nach Level Tiefe mehr Leben, mehr Schaden geben und höhere
     * Geschwindigkeiten.
     */
    public Demon(int levelDepth) {
        super();
        setup(levelDepth);
    }

    @Override
    public void setup(int levelDepth) {
        this.attackDamage = 1;
        this.lifePoints = 10;
        this.xSpeed = 0.05f;
        this.ySpeed = 0.05f;
        this.diagonal = false;
        this.pathToIdleLeft = "monster/demon/idleLeft";
        this.pathToIdleRight = "monster/demon/idleRight";
        this.pathToRunLeft = "monster/demon/runLeft";
        this.pathToRunRight = "monster/demon/runRight";

        this.hit = AnimationBuilder.buildAnimation("monster/demon/hit");
        this.die = AnimationBuilder.buildAnimation("monster/demon/hit");

        health = new HealthComponent(this, lifePoints, this, hit, die);
        skillComponent = new SkillComponent(this);
        setupCombatSkill();
        setupVelocityComponent();
        setupAnimationComponent();

        new PositionComponent(this);
        new HitboxComponent(this, this, this::onCollisionLeave);
        new HealingComponent(this, 3, 1, 3);
        new AIComponent(
            this,
            new CombatAI(1.5F, combatFight),
            new PatrouilleWalk(2f, 4, 1000, PatrouilleWalk.MODE.BACK_AND_FORTH),
            new RangeTransition(2));

        this.lifePoints += levelDepth * 0.5;
        this.attackDamage += levelDepth * 0.3;
        this.xSpeed += levelDepth * 0.02;
        this.ySpeed += levelDepth * 0.02;
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
    private void onCollisionLeave(Entity entity, Entity entity1, Tile.Direction direction) {}

    /**
     * English: The function is called as soon as different entities collide with each other. Then
     * certain instructions can be executed.
     */
    /**
     * German: Die Funktion wird aufgerufen, sobald unterschiedliche Entities miteinander
     * kollidieren. Da können dann bestimmte Anweisungen ausgeführt werden.
     */
    public void onCollision(Entity entity, Entity entity1, Tile.Direction direction) {}

    private void setupCombatSkill() {
        skillComponent.addSkill(
                combatFight =
                        new Skill(
                                new MonsterCombat(
                                        1,
                                        "animation/standardCombat.png",
                                        "animation/standardCombat.png"),
                                2F));
    }

    /*
     As soon as the entity dies, the content of the function is executed.
    */
    @Override
    public void onDeath(Entity entity) {}


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

    @Override
    public boolean isInFightMode(Entity entity) {
        return false;
    }
}
