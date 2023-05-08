package ecs.items.ImplementedItems;

import dslToGame.AnimationBuilder;
import ecs.components.InventoryComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.*;
import starter.Game;
import tools.Point;

import java.util.ArrayList;
import java.util.List;

public class Bag extends ItemData implements IOnCollect, IOnUse,IOnDrop {

    private List<ItemData> list;
    private final int maxsize = 5;

    public Bag() {
        super(
            ItemType.Bag,
            AnimationBuilder.buildAnimation("items.bag"),
            AnimationBuilder.buildAnimation("items.bag"),
            "Bag",
            "Holds up to 5 Items of an specific Item"
        );
        this.setOnCollect(this);
        this.setOnDrop(this);
        this.setOnUse(this);

        list = new ArrayList<>();
        Entity worldItemEntity = WorldItemBuilder.buildWorldItem(this);
        new PositionComponent(worldItemEntity);
    }

    public boolean addToBag(ItemData itemData) {
        if(list.size()>=maxsize) return false;
        return list.add(itemData);
    }

    public void removeFromBag(ItemData itemData) {
        list.remove(itemData);
    }

    public List<ItemData> getBag() {
        return list;
    }

    @Override
    public void onCollect(Entity WorldItemEntity, Entity whoCollides) {
        if(whoCollides instanceof Hero) {
            InventoryComponent inventoryCompnent =
                (InventoryComponent) whoCollides.getComponent(InventoryComponent.class).get();

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

    }
}
