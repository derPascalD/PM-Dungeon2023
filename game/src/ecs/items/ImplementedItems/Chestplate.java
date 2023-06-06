package ecs.items.ImplementedItems;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.items.*;
import graphic.Animation;
import tools.Point;

/** Gives the Hero more Health upon collecting */
public class Chestplate extends ItemData implements IOnCollect, IOnDrop, IOnUse {

    /** Creates a Chestplate item and spawns it in the Level at a random spot */
    public Chestplate() {
        super(
                ItemType.Armor,
                AnimationBuilder.buildAnimation("items.chestplate"),
                AnimationBuilder.buildAnimation("items.chestplate"),
                "Chestplate",
                "Protects the Player");
        this.setOnCollect(this);
        this.setOnDrop(this);
        this.setOnUse(this);

        Entity worldItemEntity = WorldItemBuilder.buildWorldItem(this);
        new PositionComponent(worldItemEntity);
    }

    public Chestplate(
            ItemType itemType,
            Animation inventoryTexture,
            Animation worldTexture,
            String itemName,
            String description) {
        super(itemType, inventoryTexture, worldTexture, itemName, description);
        this.setOnCollect(this);
        this.setOnUse(this);
    }

    /**
     * The item gets collected if the Hero has any space left in the Inventory. The item gives the
     * Hero 10 more healthpoints
     *
     * @param WorldItemEntity the item thats collected
     * @param whoCollides the Hero who collects the item
     */
    @Override
    public void onCollect(Entity WorldItemEntity, Entity whoCollides) {
        if (defaultOnCollect(WorldItemEntity, whoCollides)) {
            HealthComponent healthComponent =
                    (HealthComponent) whoCollides.getComponent(HealthComponent.class).get();
            healthComponent.setMaximalHealthpoints(healthComponent.getMaximalHealthpoints() + 10);
            healthComponent.setCurrentHealthpoints(healthComponent.getCurrentHealthpoints() + 10);
        }
    }

    @Override
    public void onDrop(Entity user, ItemData which, Point position) {}

    @Override
    public void onUse(Entity e, ItemData item) {}
}
