package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import starter.Game;
import tools.Point;

/**
 * In the class, the melee behavior is managed for the entities. Attacks always take place in x
 * direction, either left or right. The class is given a melee and then edited and implemented here
 * in the class.
 */
public abstract class CombatAttackSkills implements ISkillFunction {
    private record Weapon(Entity entity, PositionComponent epc, AnimationComponent ac) {}

    private final String pathCombatRight;
    private final String pathCombatLeft;
    private Damage combatDamage;
    private Point checkSetPoint;

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
        Animation currentAnimation;
        Weapon wp = createWeapon(entity);

        float leftRight;
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
        new PositionComponent(wp.entity(), weaponStartPoint);
        new AnimationComponent(wp.entity(), currentAnimation);
        new VelocityComponent(
                wp.entity(), (0.08F * leftRight), 0, currentAnimation, currentAnimation);

        // Position from the Weapon
        new ProjectileComponent(
                wp.entity(),
                weaponStartPoint,
                new Point(weaponStartPoint.x + (0.5F) * leftRight, weaponStartPoint.y));

        if (entity instanceof Hero hero) {
            DamageComponent dC = (DamageComponent) hero.getComponent(DamageComponent.class).get();
            combatDamage = new Damage(dC.getMeleeDamage(), combatDamage.damageType(), null);
        }

        ICollide collide =
                // b gets the damage
                (a, b, from) -> {
                    if (b != entity && b.getComponent(HealthComponent.class).isPresent()) {
                        HealthComponent hc =
                                (HealthComponent) b.getComponent(HealthComponent.class).get();

                        if ((hc.getCurrentHealthpoints() - combatDamage.damageAmount()) <= 0
                                && entity instanceof Hero hero) {
                            hero.addKilledMonsters(b);
                        }
                        System.out.println(combatDamage);
                        hc.receiveHit(combatDamage);

                        Game.removeEntity(wp.entity());
                        if (wp.ac().getCurrentAnimation() == wp.ac().getIdleRight()
                                && b.getComponent(PositionComponent.class).isPresent()) {
                            PositionComponent pc =
                                    (PositionComponent)
                                            b.getComponent(PositionComponent.class).get();
                            checkSetPoint = new Point(pc.getPosition().x + 1F, pc.getPosition().y);
                            if (Game.currentLevel
                                    .getTileAt(checkSetPoint.toCoordinate())
                                    .isAccessible()) {
                                pc.setPosition(
                                        new Point(pc.getPosition().x + 1F, pc.getPosition().y));
                            }
                        } else if (wp.ac().getCurrentAnimation() == wp.ac().getIdleLeft()
                                && b.getComponent(PositionComponent.class).isPresent()) {
                            PositionComponent pc =
                                    (PositionComponent)
                                            b.getComponent(PositionComponent.class).get();
                            checkSetPoint = new Point(pc.getPosition().x - 1F, pc.getPosition().y);
                            if (Game.currentLevel
                                    .getTileAt(checkSetPoint.toCoordinate())
                                    .isAccessible()) {
                                pc.setPosition(
                                        new Point(pc.getPosition().x - 1F, pc.getPosition().y));
                            }
                        }
                    }
                };

        new HitboxComponent(wp.entity(), collide, null);
    }

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
