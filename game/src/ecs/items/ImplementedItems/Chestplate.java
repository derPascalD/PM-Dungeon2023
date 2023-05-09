package ecs.items.ImplementedItems;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.components.InventoryComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.*;
import starter.Game;
import tools.Point;

/**
 * Gives the Hero more Health upon collecting
 */
public class Chestplate extends ItemData implements IOnCollect, IOnDrop,IOnUse {


    /**
     * Creates a Chestplate item and spawns it in the Level at a random spot
     */
    public Chestplate() {
        super(
            ItemType.Armor,
            AnimationBuilder.buildAnimation("items.chestplate"),
            AnimationBuilder.buildAnimation("items.chestplate"),
            "Chestplate",
            "Protects the Player"
        );
        this.setOnCollect(this);
        this.setOnDrop(this);
        this.setOnUse(this);

        Entity worldItemEntity = WorldItemBuilder.buildWorldItem(this);
        new PositionComponent(worldItemEntity);
    }

    /**
     * The item gets collected if the Hero has any space left in the Inventory.
     * The item gives the Hero 10 more healthpoints
     * @param WorldItemEntity the item thats collected
     * @param whoCollides the Hero who collects the item
     */
    @Override
    public void onCollect(Entity WorldItemEntity, Entity whoCollides) {
        if(whoCollides instanceof Hero) {
            InventoryComponent inventoryCompnent =
                (InventoryComponent) whoCollides.getComponent(InventoryComponent.class).get();

            if (inventoryCompnent.getMaxSize() != inventoryCompnent.filledSlots()) {
                inventoryCompnent.addItem(this);
                HealthComponent healthComponent =
                    (HealthComponent) whoCollides.getComponent(HealthComponent.class).get();
                healthComponent.setMaximalHealthpoints(healthComponent.getMaximalHealthpoints()+10);
                healthComponent.setCurrentHealthpoints(healthComponent.getCurrentHealthpoints()+10);
                Game.removeEntity(WorldItemEntity);
                System.out.println(this.getItemName() + " collected");
            } else {
                System.out.println("Inventory full, didnt pick up the Item");
            }
        }
    }

    @Override
    public void onDrop(Entity user, ItemData which, Point position) {

    }

    @Override
    public void onUse(Entity e, ItemData item) {

    }
}
