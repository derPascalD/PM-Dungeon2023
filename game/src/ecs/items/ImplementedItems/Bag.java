package ecs.items.ImplementedItems;

import dslToGame.AnimationBuilder;
import ecs.components.DamageComponent;
import ecs.components.InventoryComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.Item;
import ecs.items.ItemData;
import ecs.items.ItemType;
import starter.Game;
import tools.Point;

public class Bags extends Item {


    public Bags() {
        super(new ItemData(
            ItemType.Armor,
            AnimationBuilder.buildAnimation("items.bag"),
            AnimationBuilder.buildAnimation("items.bag"),
            "Bag",
            "Holds up to 5 Items of an specific Item"
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
                System.out.println(itemData.getItemName() + " collected");
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
