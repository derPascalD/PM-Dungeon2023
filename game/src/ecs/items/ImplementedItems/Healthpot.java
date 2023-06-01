package ecs.items.ImplementedItems;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.components.InventoryComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.items.*;
import java.util.logging.Logger;

import graphic.Animation;
import tools.Point;

/** Can be used after collecting to gain 10 Healthpoints back */
public class Healthpot extends ItemData implements IOnCollect, IOnDrop, IOnUse {

    private int healAmount = 0;
    private static int useCount = 0;

    /** Creates a Healthpot item and spawns in the Level at a random spot */
    public Healthpot() {
        super(
            ItemType.Healing,
            AnimationBuilder.buildAnimation("items.healthpot"),
            AnimationBuilder.buildAnimation("items.healthpot"),
            "Healthpot",
            "Heals the Player on Use");
        this.setOnCollect(this);
        this.setOnDrop(this);
        this.setOnUse(this);
        healAmount = 10;
        Entity worldItemEntity = WorldItemBuilder.buildWorldItem(this);
        new PositionComponent(worldItemEntity);
    }

    public Healthpot(ItemType itemType,
                     Animation inventoryTexture,
                     Animation worldTexture,
                     String itemName,
                     String description) {
        super(itemType, inventoryTexture, worldTexture, itemName, description);
        this.setOnCollect(this);
        this.setOnUse(this);
        healAmount = 10;
    }

    /**
     * Heals the Hero for 10 Healthpoints
     *
     * @param e the Hero
     */
    private void healHero(Entity e) {
        HealthComponent healthComponent =
            (HealthComponent) e.getComponent(HealthComponent.class).get();
        healthComponent.setCurrentHealthpoints(
            healthComponent.getCurrentHealthpoints() + healAmount);
        Logger.getLogger(this.getClass().getName())
            .info("Player got healed for " + healAmount + " HP");
        useCount++;
    }

    /**
     * The item gets collected if the Hero has any space left in the Inventory or in a Bag in his
     * Inventory.
     *
     * @param WorldItemEntity the item thats collected
     * @param whoCollides the Hero who collects the item
     */
    @Override
    public void onCollect(Entity WorldItemEntity, Entity whoCollides) {
        defaultOnCollect(WorldItemEntity, whoCollides);
    }

    @Override
    public void onDrop(Entity user, ItemData which, Point position) {}

    /**
     * Gives the healthpoitns upon usage, item is either in the Inventory or in a Bag in the
     * Inventory Item gets removed after usage
     *
     * @param e the Hero
     * @param item the item thats used
     */
    @Override
    public void onUse(Entity e, ItemData item) {
        InventoryComponent inventoryCompnent =
            (InventoryComponent) e.getComponent(InventoryComponent.class).get();

        for (ItemData itemFromInventory : inventoryCompnent.getItems()) {
            if (itemFromInventory instanceof Bag) {
                Bag bag = (Bag) itemFromInventory;
                if (bag.removeFromBag(item)) {
                    healHero(e);
                    return;
                }
            }
        }
        inventoryCompnent.removeItem(item);
        healHero(e);
    }

    /**
     * @return How many Healthpots the hero has used
     */
    public static int getUseCount() {
        return useCount;
    }
}
