package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import java.util.logging.Logger;
import starter.Game;
import tools.Point;

/**
 * In the class, the melee behavior is managed for the entities. Attacks always take place in x
 * direction, either left or right. The class is given a melee and then edited and implemented here
 * in the class.
 */
public abstract class CombatAttackSkills implements ISkillFunction {
    private record Weapon(Entity weapon, PositionComponent epc, AnimationComponent ac) {}

    private final Logger combatLogger = Logger.getLogger(getClass().getName());
    private final String pathCombatRight;
    private final String pathCombatLeft;
    private Damage combatDamage;
    private Animation currentAnimation;

    /**
     * @param pathCombatLeft Animation Path Left for the Entity
     * @param pathCombatRight Animation Path Right for the Entity
     * @param combatDamage Damage for the CombatAttack
     */
    public CombatAttackSkills(String pathCombatLeft, String pathCombatRight, Damage combatDamage) {
        this.pathCombatRight = pathCombatRight;
        this.pathCombatLeft = pathCombatLeft;
        this.combatDamage = combatDamage;
    }

    /**
     * In the method, the combat behavior is implemented. Depending on how the entity stands, the
     * attack is executed differently.
     *
     * @param entity which uses the skill
     */
    @Override
    public void execute(Entity entity) {
        combatLogger.info("Launches a melee attack");
        Weapon wp = createWeapon(entity);
        int leftRight;
        Point weaponStartPoint;
        if (wp.ac().getCurrentAnimation() == wp.ac().getIdleLeft()) {
            weaponStartPoint = new Point(wp.epc().getPosition().x - 0.5F, wp.epc().getPosition().y);
            currentAnimation = AnimationBuilder.buildAnimation(pathCombatLeft);
            leftRight = -1;
        } else if (wp.ac().getCurrentAnimation() == wp.ac().getIdleRight()) {
            weaponStartPoint = new Point(wp.epc().getPosition().x + 0.2F, wp.epc().getPosition().y);
            currentAnimation = AnimationBuilder.buildAnimation(pathCombatRight);
            leftRight = 1;
        } else {
            return;
        }
        setCombatComponents(wp, weaponStartPoint, leftRight);
        setDamage(entity);
        setCollide(wp, entity);
    }

    /*
    Creates the melee blow.
    */
    private void setCombatComponents(Weapon wp, Point weaponStartPoint, int leftRight) {
        new PositionComponent(wp.weapon(), weaponStartPoint);
        new AnimationComponent(wp.weapon(), currentAnimation);
        new VelocityComponent(
            wp.weapon(), (0.08F * leftRight), 0, currentAnimation, currentAnimation);
        new ProjectileComponent(
            wp.weapon(),
            weaponStartPoint,
            new Point(weaponStartPoint.x + (0.5F) * leftRight, weaponStartPoint.y));
    }

    /*
    As soon as the close-capture attack collides with an entity,
    the entity is damaged, and it's position is changed.
    */
    private void setCollide(Weapon wp, Entity entity) {
        ICollide collide =

            // b gets the damage
            (a, b, from) -> {
                if (b != entity && b.getComponent(HealthComponent.class).isPresent()) {
                    HealthComponent hc =
                        (HealthComponent) b.getComponent(HealthComponent.class).get();

                    if ((hc.getCurrentHealthpoints() - combatDamage.damageAmount()) <= 0
                        && entity instanceof Hero hero) {
                        hero.addKilledMonsters(b);
                        combatLogger.info("Was added to the list of killed monsters");
                    }
                    hc.receiveHit(combatDamage);
                    Game.removeEntity(wp.weapon());
                    throwback(wp, b);
                }
            };

        new HitboxComponent(wp.weapon(), collide, null);
    }

    /*
    Resets the position of an entity a few steps backwards.
    */
    private void throwback(Weapon wp, Entity b) {
        Point checkSetPoint;
        if (wp.ac().getCurrentAnimation() == wp.ac().getIdleRight()
            && b.getComponent(PositionComponent.class).isPresent()) {
            PositionComponent pc =
                (PositionComponent) b.getComponent(PositionComponent.class).get();
            checkSetPoint = new Point(pc.getPosition().x + 1F, pc.getPosition().y);
            if (Game.currentLevel.getTileAt(checkSetPoint.toCoordinate()).isAccessible()) {
                pc.setPosition(new Point(pc.getPosition().x + 1F, pc.getPosition().y));
            }
        } else if (wp.ac().getCurrentAnimation() == wp.ac().getIdleLeft()
            && b.getComponent(PositionComponent.class).isPresent()) {
            PositionComponent pc =
                (PositionComponent) b.getComponent(PositionComponent.class).get();
            checkSetPoint = new Point(pc.getPosition().x - 1F, pc.getPosition().y);
            if (Game.currentLevel.getTileAt(checkSetPoint.toCoordinate()).isAccessible()) {
                pc.setPosition(new Point(pc.getPosition().x - 1F, pc.getPosition().y));
            }
        }
    }

    /*
    Sets the damage to the melee.
    */
    private void setDamage(Entity entity) {
        if (entity instanceof Hero hero) {
            DamageComponent dC = (DamageComponent) hero.getComponent(DamageComponent.class).get();
            combatDamage = new Damage(dC.getMeleeDamage(), combatDamage.damageType(), null);
        }
    }

    /*
    Creates the position/animation component of the close combat.
    */
    private Weapon createWeapon(Entity entity) {
        Entity weapon = new Entity();
        PositionComponent epc =
            (PositionComponent)
                entity.getComponent(PositionComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("PositionComponent"));

        AnimationComponent ac =
            (AnimationComponent)
                entity.getComponent(AnimationComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("AnimationComponent"));

        return new Weapon(weapon, epc, ac);
    }
}
