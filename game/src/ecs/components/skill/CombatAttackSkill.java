package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.Monsters.Monster;
import graphic.Animation;
import level.elements.tile.Tile;
import starter.Game;
import tools.Point;

public abstract class CombatAttackSkill implements ISkillFunction {

    private String pathToTexturesOfCombatRight;
    private String pathToTexturesOfCombatLeft;
    private float combatRange;
    private Point hitboxSize;
    private Damage combatDamage;
    private Point weaponStartPoint;

    private Animation currentAnimation;


    public CombatAttackSkill(
        String pathToTexturesOfCombatLeft,
        String pathToTexturesOfCombatRight,
        Point hitboxSize,
        Damage combatDamage,
        float combatRange) {
        this.pathToTexturesOfCombatRight = pathToTexturesOfCombatRight;
        this.pathToTexturesOfCombatLeft = pathToTexturesOfCombatLeft;
        this.hitboxSize = hitboxSize;
        this.combatDamage = combatDamage;
        this.combatRange = combatRange;
    }

    public CombatAttackSkill(
        String pathToTexturesOfCombatLeft,
        String pathToTexturesOfCombatRight,
        Point hitboxSize,
        Damage combatDamage) {
        this.pathToTexturesOfCombatRight = pathToTexturesOfCombatRight;
        this.pathToTexturesOfCombatLeft = pathToTexturesOfCombatLeft;
        this.hitboxSize = hitboxSize;
        this.combatDamage = combatDamage;


    }




    @Override
    public void execute(Entity entity) {
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


        if (ac.getCurrentAnimation() == ac.getIdleLeft()) {
            weaponStartPoint = new Point(epc.getPosition().x - 1, epc.getPosition().y);
            currentAnimation = AnimationBuilder.buildAnimation(pathToTexturesOfCombatLeft);
        } else if (ac.getCurrentAnimation() == ac.getIdleRight()) {
            weaponStartPoint = new Point(epc.getPosition().x + 0.2F, epc.getPosition().y);
            currentAnimation = AnimationBuilder.buildAnimation(pathToTexturesOfCombatRight);
        } else {
            return;
        }
        new PositionComponent(weapon, weaponStartPoint);
        new AnimationComponent(weapon, currentAnimation);
        new VelocityComponent(weapon, 0.08F, 0, currentAnimation, currentAnimation);

        // Position from the Weapon

        new ProjectileComponent(weapon, weaponStartPoint, new Point(weaponStartPoint.x - combatRange, weaponStartPoint.y));

        ICollide collide =
            (a, b, from) -> {
                if (b != entity) {
                    b.getComponent(HealthComponent.class).ifPresent(hc -> {
                        ((HealthComponent) hc).receiveHit(combatDamage);
                        Game.removeEntity(weapon);
                    });
                }
                if (b != entity && ac.getCurrentAnimation() == ac.getIdleRight()) {
                    b.getComponent(VelocityComponent.class).ifPresent(pc -> {
                            ((VelocityComponent) pc).setCurrentXVelocity(0.5F + ((VelocityComponent) pc).getXVelocity());
                        }
                    );
                } else if (b != entity && ac.getCurrentAnimation() == ac.getIdleLeft()) {
                    b.getComponent(VelocityComponent.class).ifPresent(pc -> {
                            ((VelocityComponent) pc).setCurrentXVelocity(-0.5F + ((VelocityComponent) pc).getXVelocity());
                        }
                    );
                }
            };


        new HitboxComponent(
            weapon, new Point(0.25f, 0.25f), hitboxSize, collide, null);


    }
}
