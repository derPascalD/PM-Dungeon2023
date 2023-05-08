package ecs.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Logger;
import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.components.InventoryComponent;
import ecs.components.ItemComponent;
import ecs.components.stats.DamageModifier;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import starter.Game;
import tools.Point;

public class Healthpot extends Item {



    public Healthpot() {
        super(new ItemData(
            ItemType.Healing,
            AnimationBuilder.buildAnimation("items.healthpot"),
            AnimationBuilder.buildAnimation("items.healthpot"),
            "Healthpot",
            "Heals the Player on Use",
            null,
            null,
            null,
            new DamageModifier()
            ));
        itemData.setOnCollect(this);
        itemData.setOnDrop(this);
        itemData.setOnUse(this);
    }


    @Override
    public void onCollect(Entity WorldItemEntity, Entity whoCollides) {
        if(whoCollides instanceof Hero) {
            InventoryComponent inventoryCompnent =
                (InventoryComponent) whoCollides.getComponent(InventoryComponent.class).get();

            if (inventoryCompnent.getMaxSize() != inventoryCompnent.filledSlots()) {
                inventoryCompnent.addItem(itemData);
                Game.removeEntity(WorldItemEntity);
                System.out.println("Healthpotion collected");
            } else {
                System.out.println("Inventory full, didnt pick up the Item");
            }
        }
    }

    @Override
    public void onDrop(Entity user, ItemData which, Point position) {
        // Mit den anderen drüber reden
    }

    @Override
    public void onUse(Entity e, ItemData item) {
        InventoryComponent inventoryCompnent =
            (InventoryComponent) e.getComponent(InventoryComponent.class).get();
        HealthComponent healthComponent =
            (HealthComponent) e.getComponent(HealthComponent.class).get();
        healthComponent.setCurrentHealthpoints(healthComponent.getCurrentHealthpoints()+10);
        inventoryCompnent.removeItem(item);
        System.out.println("Healpotion used, gained 10 HP");
    }
}
