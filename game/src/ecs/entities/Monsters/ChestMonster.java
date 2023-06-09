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
import ecs.entities.Chest;
import ecs.entities.Entity;
import ecs.items.ImplementedItems.Chestplate;
import ecs.items.ImplementedItems.Healthpot;
import ecs.items.ImplementedItems.SimpleWand;
import ecs.items.ItemData;
import ecs.items.ItemDataGenerator;
import level.elements.tile.Tile;
import starter.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * new Monster which spawn only if the hero interacts before with the MonsterChest.
 */

public class ChestMonster extends Monster {

    private transient Skill combatSkill;
    private transient SkillComponent skillComponent;
    private Chest chest;

    public ChestMonster(int levelDepth, Chest chest) {
        super();
        this.chest = chest;
        this.attackDamage = 1;
        this.lifePoints = 10;
        this.xSpeed = 0.05f;
        this.ySpeed = 0.05f;
        this.diagonal = false;
        this.pathToIdleLeft = "chestMonster";
        this.pathToIdleRight = "chestMonster";
        this.pathToRunLeft = "chestMonster";
        this.pathToRunRight = "chestMonster";

        this.hit = AnimationBuilder.buildAnimation("monster/demon/hit");
        this.die = AnimationBuilder.buildAnimation("monster/demon/hit");

        health = new HealthComponent(this, lifePoints, this, hit, die);
        skillComponent = new SkillComponent(this);
        setupCombatSkill();
        setupVelocityComponent();
        setupAnimationComponent();
        setupPositionComponent(this.chest);

        new HitboxComponent(this, this, this::onCollisionLeave);
        new HealingComponent(this, 5);


        // adding the AIComponent
        new AIComponent(
            this,
            new CombatAI(1.5F, combatSkill),
            new PatrouilleWalk(2f, 4, 1000, PatrouilleWalk.MODE.BACK_AND_FORTH),
            new RangeTransition(2));

        this.lifePoints += levelDepth * 0.5;
        this.attackDamage += levelDepth * 0.3;
        this.xSpeed += levelDepth * 0.02;
        this.ySpeed += levelDepth * 0.02;
    }

    @Override
    public void setup(int levelDepth) {}

    private void onCollisionLeave(Entity entity, Entity entity1, Tile.Direction direction) {}

    private void setupCombatSkill() {
        skillComponent.addSkill(
            combatSkill =
                new Skill(
                    new MonsterCombat(
                        1,
                        "animation/standardCombat.png",
                        "animation/standardCombat.png"),
                    2F));
    }

    private void setupPositionComponent(Chest chest){
        PositionComponent positionComponent =
            (PositionComponent)
                chest.getComponent(PositionComponent.class)
                    .orElseThrow(
                        () ->
                            new IllegalStateException(
                                "Entity does not have a PositionComponent"));
        new PositionComponent(this).setPosition(positionComponent.getPosition());
    }

    @Override
    public void onDeath(Entity entity) {
        spawnOriginalChestwitItems(entity);
        Game.getEntitiesToRemove().add(this);
    }

    /**
     * Generates a new chest with Items on the last position of the monster before it died.
     * @param entity ChestMonster
     */
    private static void spawnOriginalChestwitItems(Entity entity) {
        //getting the last position of the entity
        PositionComponent positionComponent =
            (PositionComponent)
                entity.getComponent(PositionComponent.class)
                    .orElseThrow(
                        () ->
                            new IllegalStateException(
                                "Entity does not have a PositionComponent"));

        // generating at least three random Items.
        List<ItemData> items = new ArrayList<>();
        ItemDataGenerator generator = new ItemDataGenerator();
        for (int i = 0; i < 3; i++) {
            items.add(generator.generateItemData());
        }

        // creating a new chest with the obove generated items.
        new Chest(items,positionComponent.getPosition());

    }

    @Override
    public boolean isInFightMode(Entity entity) {return false;}
    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {}
}
