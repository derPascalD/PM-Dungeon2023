package ecs.items;

import ecs.components.InventoryComponent;
import ecs.components.ItemComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.ImplementedItems.Bag;
import java.util.logging.Logger;
import starter.Game;

public interface IOnCollect {

    void onCollect(Entity WorldItemEntity, Entity whoCollides);

    /**
     * Default Collect that adds the Item to the Entities Inventory or Bag
     *
     * @param WorldItemEntity Entity that represents the Item
     * @param whoCollides Entity that represents the Hero
     * @return Returns true when the Item was added to the Bag or Inventory else it returns false
     */
    default boolean defaultOnCollect(Entity WorldItemEntity, Entity whoCollides) {
        if (whoCollides instanceof Hero) {
            InventoryComponent inventoryCompnent =
                    (InventoryComponent) whoCollides.getComponent(InventoryComponent.class).get();
            ItemComponent itemComponent =
                    (ItemComponent) WorldItemEntity.getComponent(ItemComponent.class).get();

            // Adds Item to Bag, if Bag is in Inventory
            for (ItemData item : inventoryCompnent.getItems()) {
                if (item instanceof Bag) {
                    Bag bag = (Bag) item;
                    if (bag.addToBag(itemComponent.getItemData())) {
                        Game.removeEntity(WorldItemEntity);
                        Logger.getLogger(this.getClass().getName())
                                .info(
                                        itemComponent.getItemData().getItemName()
                                                + " got collected in a Bag");
                        return true;
                    }
                }
            }

            if (inventoryCompnent.getMaxSize() != inventoryCompnent.filledSlots()) {
                inventoryCompnent.addItem(itemComponent.getItemData());
                Game.removeEntity(WorldItemEntity);
                Logger.getLogger(this.getClass().getName())
                        .info(itemComponent.getItemData().getItemName() + " got collected");
                return true;
            } else {
                Logger.getLogger(this.getClass().getName()).info("Inventory full");
            }
        }
        return false;
    }
    ;
}
