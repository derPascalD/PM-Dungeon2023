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

public class Healthpot extends ItemData implements IOnCollect, IOnDrop,IOnUse {

    public Healthpot() {
        super(
            ItemType.Healing,
            AnimationBuilder.buildAnimation("items.healthpot"),
            AnimationBuilder.buildAnimation("items.healthpot"),
            "Healthpot",
            "Heals the Player on Use"
            );
        this.setOnCollect(this);
        this.setOnDrop(this);
        this.setOnUse(this);

        Entity worldItemEntity = WorldItemBuilder.buildWorldItem(this);
        new PositionComponent(worldItemEntity);
    }

    private void healHero(Entity e) {
        HealthComponent healthComponent =
            (HealthComponent) e.getComponent(HealthComponent.class).get();
        healthComponent.setCurrentHealthpoints(healthComponent.getCurrentHealthpoints()+10);
        System.out.println("Healpotion used, gained 10 HP");
    }

    @Override
    public void onCollect(Entity WorldItemEntity, Entity whoCollides) {
        if(whoCollides instanceof Hero) {
            InventoryComponent inventoryCompnent =
                (InventoryComponent) whoCollides.getComponent(InventoryComponent.class).get();

            // Adds Item to Bag, if Bag is in Inventory
            for(ItemData item:inventoryCompnent.getItems()) {
                if(item instanceof Bag) {
                    Bag bag = (Bag)item;
                    if(bag.addToBag(this)) {
                        Game.removeEntity(WorldItemEntity);
                        System.out.println(this.getItemName() + " collected");
                        return;
                    }
                }
            }

            if (inventoryCompnent.getMaxSize() != inventoryCompnent.filledSlots()) {
                inventoryCompnent.addItem(this);
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
        InventoryComponent inventoryCompnent =
            (InventoryComponent) e.getComponent(InventoryComponent.class).get();

        for(ItemData itemFromInventory:inventoryCompnent.getItems()) {
            if(itemFromInventory instanceof Bag) {
                Bag bag = (Bag)itemFromInventory;
                healHero(e);
                bag.removeFromBag(item);
                return;
            }
        }

        inventoryCompnent.removeItem(item);
        healHero(e);
    }
}
