package ecs.systems;

import com.badlogic.gdx.Gdx;
import configuration.KeyboardConfig;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.ImplementedItems.Bag;
import ecs.items.ItemData;
import ecs.items.ItemType;
import ecs.tools.interaction.InteractionTool;
import java.util.logging.Logger;
import starter.Game;

/** Used to control the player */
public class PlayerSystem extends ECS_System {

    private record KSData(Entity e, PlayableComponent pc, VelocityComponent vc) {}

    @Override
    public void update() {
        Game.getEntities().stream()
                .flatMap(e -> e.getComponent(PlayableComponent.class).stream())
                .map(pc -> buildDataObject((PlayableComponent) pc))
                .forEach(this::checkKeystroke);
    }

    private void checkKeystroke(KSData ksd) {
        if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_UP.get()))
            ksd.vc.setCurrentYVelocity(1 * ksd.vc.getYVelocity());
        else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_DOWN.get()))
            ksd.vc.setCurrentYVelocity(-1 * ksd.vc.getYVelocity());
        else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_RIGHT.get()))
            ksd.vc.setCurrentXVelocity(1 * ksd.vc.getXVelocity());
        else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_LEFT.get()))
            ksd.vc.setCurrentXVelocity(-1 * ksd.vc.getXVelocity());
        if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get()))
            showInventoryInConsole(ksd.e);

        if (Gdx.input.isKeyJustPressed(KeyboardConfig.HEAL_POTION.get())) {
            useHealPotion(ksd.e);
        }
        if (Gdx.input.isKeyJustPressed(KeyboardConfig.GRENADE_LAUNCH.get())) {
            ksd.pc.getSkillSlot4().ifPresent(skill -> skill.execute(ksd.e));
        }

        if (Gdx.input.isKeyJustPressed(KeyboardConfig.NINJA_BLADE.get())) {
            ksd.pc.getSkillSlot5().ifPresent(skill -> skill.execute(ksd.e));
        }

        if (Gdx.input.isKeyPressed(KeyboardConfig.INTERACT_WORLD.get()))
            InteractionTool.interactWithClosestInteractable(ksd.e);
        if (Gdx.input.isKeyJustPressed(KeyboardConfig.EQUIP_WEAPON.get())) equipWeapon(ksd.e);
        else if (Gdx.input.isKeyPressed(KeyboardConfig.FIRST_SKILL.get()))
            ksd.pc.getSkillSlot1().ifPresent(skill -> skill.execute(ksd.e));
        else if (Gdx.input.isKeyPressed(KeyboardConfig.SECOND_SKILL.get()))
            ksd.pc.getSkillSlot2().ifPresent(skill -> skill.execute(ksd.e));
        else if (Gdx.input.isKeyPressed(KeyboardConfig.THIRD_SKILL.get()))
            ksd.pc.getSkillSlot3().ifPresent(skill -> skill.execute(ksd.e));
        else if (Gdx.input.isKeyPressed(KeyboardConfig.COMBAT_ATTACK.get()) && meleeActive(ksd.e))
            ksd.pc.getCombatSkill().ifPresent(skill -> skill.execute(ksd.e));
    }

    /*
     * Displays the Inventory of the Hero in the console
     *
     * @param e Entity which inventory gets displayed, mostly the Hero
     */
    private void showInventoryInConsole(Entity e) {
        InventoryComponent inventoryCompnent =
                (InventoryComponent) e.getComponent(InventoryComponent.class).get();
        System.out.println(
                "Inventory of the Hero: "
                        + inventoryCompnent.filledSlots()
                        + " / "
                        + inventoryCompnent.getMaxSize());
        for (ItemData item : inventoryCompnent.getItems()) {
            // Iterates through Bag when Bag exists
            if (item instanceof Bag) {
                System.out.println("+ Bag:");
                Bag bag = (Bag) item;
                for (ItemData itemInBag : bag.getBag()) {
                    System.out.println(
                            "+-- " + itemInBag.getItemName() + ": " + itemInBag.getDescription());
                }
            } else {
                System.out.println("+ " + item.getItemName() + ": " + item.getDescription());
            }
        }
    }

    /*
    Makes it possible to equip a melee weapon
    */
    private void equipWeapon(Entity e) {
        if (e instanceof Hero hero && hero.isEquipWeapon()) {
            hero.setEquipWeapon(false);
            setHeroWithWeapon(hero);
            Logger.getLogger(e.getClass().getName())
                    .info(e.getClass().getSimpleName() + " melee weapon returned");
        } else if (e instanceof Hero hero && !hero.isEquipWeapon()) {
            hero.setEquipWeapon(true);
            setHeroWithoutWeapon(hero);
            Logger.getLogger(e.getClass().getName())
                    .info(e.getClass().getSimpleName() + " close combat weapon equipped");
        }
    }

    // Set the Path Hero with Weapon
    private void setHeroWithWeapon(Hero hero) {
        hero.setPathToIdleLeft("knight/idleCombatLeft");
        hero.setPathToIdleRight("knight/idleCombatRight");
        hero.setPathToRunLeft("knight/runCombatLeft");
        hero.setPathToRunRight("knight/runCombatRight");
        heroComponents(hero);
    }

    // The Animation and VelocityComponent for the Hero Animation
    private void heroComponents(Hero hero) {
        AnimationComponent aC =
                (AnimationComponent) hero.getComponent(AnimationComponent.class).get();
        aC.setIdleLeft(AnimationBuilder.buildAnimation(hero.getPathToIdleLeft()));
        aC.setIdleRight(AnimationBuilder.buildAnimation(hero.getPathToIdleRight()));
        VelocityComponent vC = (VelocityComponent) hero.getComponent(VelocityComponent.class).get();
        vC.setMoveLeftAnimation(AnimationBuilder.buildAnimation(hero.getPathToRunLeft()));
        vC.setMoveRightAnimation(AnimationBuilder.buildAnimation(hero.getPathToRunRight()));
    }

    // Set the Path Hero without Weapon
    private void setHeroWithoutWeapon(Hero hero) {
        hero.setPathToIdleLeft("knight/idleLeft");
        hero.setPathToIdleRight("knight/idleRight");
        hero.setPathToRunLeft("knight/runLeft");
        hero.setPathToRunRight("knight/runRight");
        heroComponents(hero);
    }

    // Method that checks whether a melee weapon is equipped or not on the Hero.
    private boolean meleeActive(Entity e) {
        if (e instanceof Hero hero) return hero.isEquipWeapon();
        return false;
    }

    /*
     * Uses an healing item from the entities inventory to heal the entity
     *
     * @param e Entity thats using the healpotion, mostly the Hero
     */
    private void useHealPotion(Entity e) {
        InventoryComponent inventoryCompnent =
                (InventoryComponent) e.getComponent(InventoryComponent.class).get();
        // Checks if healpotion is in Bag
        for (ItemData item : inventoryCompnent.getItems()) {
            if (item instanceof Bag) {
                Bag bag = (Bag) item;
                if (bag.getBagType() == ItemType.Healing) {
                    for (ItemData itemInBag : bag.getBag()) {
                        itemInBag.triggerUse(e);
                        return;
                    }
                }
            }
        }
        // Checks for normal Inventory
        for (ItemData item : inventoryCompnent.getItems()) {
            if (item.getItemType().equals(ItemType.Healing)) {
                item.triggerUse(e);
                return;
            }
        }
    }

    private KSData buildDataObject(PlayableComponent pc) {
        Entity e = pc.getEntity();

        VelocityComponent vc =
                (VelocityComponent)
                        e.getComponent(VelocityComponent.class)
                                .orElseThrow(PlayerSystem::missingVC);

        return new KSData(e, pc, vc);
    }

    private static MissingComponentException missingVC() {
        return new MissingComponentException("VelocityComponent");
    }
}
