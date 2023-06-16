package ecs.entities.Monsters;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.BossAI;
import ecs.components.ai.idle.BossIdleWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.*;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.entities.Hero;
import level.elements.tile.Tile;
import starter.Game;
import tools.Point;

public class Boss extends Monster{
    private transient Skill combatSkill;
    private transient Skill distanceSkill;
    private transient PositionComponent hc;
    private transient VelocityComponent vc;
    private Hero hero = (Hero) Game.getHero().get();
    private transient SkillComponent skillComponent;

    /**
     *
     * @param levelDepth is the current Level
     */
    public Boss(int levelDepth) {
        setup(levelDepth);

    }

    private void setupCombatSkill() {

        skillComponent.addSkill(
            combatSkill =
                new Skill(
                    new FireballSkill(() -> hc.getPosition()), 0));
    }

    private void setupDistanceSkill()
    {
        skillComponent.addSkill(distanceSkill = new Skill(new NinjaBlade(

            10,
            false,
            "skills/ninjablade/ninja_blade_left",
            0.25f,
            new Damage(2, DamageType.PHYSICAL, null),
            new Point(0.5f, 0.5f),
            () -> hc.getPosition(),
            5f),
            1));
    }

    private void onCollisionLeave(Entity entity, Entity entity1, Tile.Direction direction) {
    }


    @Override
    public void onDeath(Entity entity) {

    }

    @Override
    public boolean isInFightMode(Entity entity) {
        return false;
    }

    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {

    }

    @Override
    public void setup(int levelDepth) {

        this.attackDamage = 1;
        this.lifePoints = 10;
        this.xSpeed = 0.05f;
        this.ySpeed = 0.05f;
        this.diagonal = false;
        this.pathToIdleLeft = "character/monster/imp/idleLeft";
        this.pathToIdleRight = "character/monster/imp/idleRight";
        this.pathToRunLeft = "character/monster/imp/runLeft";
        this.pathToRunRight = "character/monster/imp/runRight";


        this.hit = AnimationBuilder.buildAnimation("monster/demon/hit");
        this.die = AnimationBuilder.buildAnimation("monster/demon/hit");
        skillComponent =  new SkillComponent(this);
        health = new HealthComponent(this, lifePoints, this, hit, die);
        hc =  (PositionComponent) hero.getComponent(PositionComponent.class).get();
        vc =  (VelocityComponent) hero.getComponent(VelocityComponent.class).get();

        setupVelocityComponent();
        setupAnimationComponent();
        setupCombatSkill();
        setupDistanceSkill();

        new HitboxComponent(this, this, this::onCollisionLeave);
        new HealingComponent(this, 5);

        // adding the AIComponent
        new AIComponent(
            this,
            new BossAI(5f,combatSkill,distanceSkill, health,vc),
            new BossIdleWalk(10f, 2f, health),
            new RangeTransition(2));

        this.lifePoints += levelDepth * 0.5;
        this.attackDamage += levelDepth * 0.3;
        this.xSpeed += levelDepth * 0.02;
        this.ySpeed += levelDepth * 0.02;

    }
}
