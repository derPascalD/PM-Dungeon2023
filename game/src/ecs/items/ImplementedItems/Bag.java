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

/**
 * Creates a Bag Item of a specific ItemType which can hold up to 5 items of that ItemType
 */
public class Bag extends ItemData implements IOnCollect, IOnUse,IOnDrop {

    private List<ItemData> list;
    private final int maxsize = 5;
    private ItemType bagType;

    /**
     * Creates a Bag item and spawns it in the Level at a random spot
     * @param bagType the ItemType the Bag can store
     */
    public Bag(ItemType bagType) {
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
        this.bagType = bagType;
        list = new ArrayList<>();
        Entity worldItemEntity = WorldItemBuilder.buildWorldItem(this);
        new PositionComponent(worldItemEntity);
    }

    /**
     * Adds the given ItemData to the Bag if
     * the itemData is allowed in the Bag and the Bag has space for the Item
     * @param itemData the item thats gonna be added to the Bag
     * @return if the operation was successful
     */
    public boolean addToBag(ItemData itemData) {
        if(itemData.getItemType()!=bagType) return false;
        if(list.size()>=maxsize) return false;
        return list.add(itemData);
    }

    /**
     * Removes given ItemData from the Bag if its exits in the Bag
     * @param itemData the item thats going to be removed from the Bag
     * @return of the operation was successful
     */
    public boolean removeFromBag(ItemData itemData) {
        return list.remove(itemData);
    }

    /**
     * Returns the content of the Bag
     * @return the Bag as a List Object
     */
    public List<ItemData> getBag() {
        return list;
    }

    /**
     * Returns the ItemType thats is allowed in the Bag
     * @return ItemType thats is allowed in the Bag
     */
    public ItemType getBagType() {
        return bagType;
    }

    /**
     * The item gets collected if the Hero has any space left in the Inventory.
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
