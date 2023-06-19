package ecs.entities.Monsters;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.BossAI;
import ecs.components.ai.idle.BossIdleWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.*;
import ecs.entities.Entity;
import level.elements.tile.Tile;

public class Boss extends Monster {
    private transient Skill combatSkill;
    private transient Skill distanceSkill;
    private Entity hero;
    private transient SkillComponent skillComponent;

    /**
     * @param levelDepth is the current Level
     */
    public Boss(int levelDepth, Entity hero) {
        this.hero = hero;

        this.attackDamage = 5;
        this.xSpeed += levelDepth * 0.01;
        this.ySpeed += levelDepth * 0.01;
        this.lifePoints = 35;
        this.diagonal = false;
        this.pathToIdleLeft = "character/monster/imp/idleLeft";
        this.pathToIdleRight = "character/monster/imp/idleRight";
        this.pathToRunLeft = "character/monster/imp/runLeft";
        this.pathToRunRight = "character/monster/imp/runRight";

        this.hit = AnimationBuilder.buildAnimation("monster/demon/hit");
        this.die = AnimationBuilder.buildAnimation("monster/demon/hit");
        skillComponent = new SkillComponent(this);
        health = new HealthComponent(this, lifePoints, this, hit, die);

        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupCombatSkill();
        setupDistanceSkill();

        new HitboxComponent(this, this, this::onCollisionLeave);
        new HealingComponent(this, 5);

        // adding the AIComponent
        new AIComponent(
                this,
                new BossAI(6f, combatSkill, distanceSkill),
                new BossIdleWalk(10f, 2f, health),
                new RangeTransition(4));
    }

    private void setupCombatSkill() {

        skillComponent.addSkill(
                combatSkill =
                        new Skill(
                                new MonsterCombat(
                                        attackDamage,
                                        "animation/standardCombat.png",
                                        "animation/standardCombat.png"),
                                1F));
    }

    private void setupDistanceSkill() {
        PositionComponent pc = (PositionComponent) hero.getComponent(PositionComponent.class).get();
        skillComponent.addSkill(
                distanceSkill = new Skill(new FireballSkill(() -> pc.getPosition()), 1));
    }

    private void onCollisionLeave(Entity entity, Entity entity1, Tile.Direction direction) {}

    @Override
    public void onDeath(Entity entity) {}

    @Override
    public boolean isInFightMode(Entity entity) {
        return false;
    }

    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {}
}
