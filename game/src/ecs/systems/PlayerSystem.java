package ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import configuration.KeyboardConfig;
import ecs.components.InventoryComponent;
import ecs.components.MissingComponentException;
import ecs.components.PlayableComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.ISkillFunction;
import ecs.components.skill.Skill;
import ecs.components.skill.SkillComponent;
import ecs.components.skill.magic.MagicSkills;
import ecs.components.skill.magic.SpeedSkill;
import ecs.entities.Entity;
import ecs.items.Healthpot;
import ecs.items.ItemData;
import ecs.items.ItemType;
import ecs.tools.interaction.InteractionTool;
import starter.Game;

/**
 * Used to control the player
 */
public class PlayerSystem extends ECS_System {

    private record KSData(Entity e, PlayableComponent pc, VelocityComponent vc) {
    }

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

        if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get())) {
            showInventoryInConsole(ksd.e);
        }

        if (Gdx.input.isKeyJustPressed(KeyboardConfig.HEAL_POTION.get())) {
            useHealPotion(ksd.e);
        }

        if (Gdx.input.isKeyPressed(KeyboardConfig.INTERACT_WORLD.get()))
            InteractionTool.interactWithClosestInteractable(ksd.e);

        // check skills
        else if (Gdx.input.isKeyPressed(KeyboardConfig.FIRST_SKILL.get()))
            ksd.pc.getSkillSlot1().ifPresent(skill -> skill.execute(ksd.e));

        else if (Gdx.input.isKeyPressed(KeyboardConfig.SECOND_SKILL.get()))
            ksd.pc.getSkillSlot2().ifPresent(skill -> skill.execute(ksd.e));

        else if (Gdx.input.isKeyPressed(KeyboardConfig.THIRD_SKILL.get())) {
            ksd.pc.getSkillSlot3().ifPresent(skill -> skill.execute(ksd.e));
        }
    }

    private void showInventoryInConsole(Entity e) {
        InventoryComponent inventoryCompnent =
            (InventoryComponent) e.getComponent(InventoryComponent.class).get();
        System.out.println("Inventory of the Hero:");
        for (ItemData item:inventoryCompnent.getItems()) {
            System.out.println(item.getItemName());
        }
    }

    private void useHealPotion(Entity e) {
        InventoryComponent inventoryCompnent =
            (InventoryComponent) e.getComponent(InventoryComponent.class).get();
        // Wenn heilpots da sind, heilen, sonst fehlermeldung
        for (ItemData item:inventoryCompnent.getItems()) {
            if(item.getItemType().equals(ItemType.Healing)) {
                item.triggerUse(e);
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
