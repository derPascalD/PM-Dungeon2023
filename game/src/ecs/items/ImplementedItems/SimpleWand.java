package ecs.items.ImplementedItems;

import dslToGame.AnimationBuilder;
import ecs.components.DamageComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.items.*;
import graphic.Animation;
import tools.Point;

/** Gives the Hero more damage upon collecting */
public class SimpleWand extends ItemData implements IOnCollect, IOnDrop, IOnUse {

    /** Creates a SimpleWand item and spawns it in the Level at a random spot */
    public SimpleWand() {
        super(
            ItemType.Weapon,
            AnimationBuilder.buildAnimation("items.simplewand"),
            AnimationBuilder.buildAnimation("items.simplewand"),
            "SimpleWand",
            "Gives the Player more Attack Damage");
        this.setOnCollect(this);
        this.setOnDrop(this);
        this.setOnUse(this);

        Entity worldItemEntity = WorldItemBuilder.buildWorldItem(this);
        new PositionComponent(worldItemEntity);
    }

    public SimpleWand(ItemType itemType,
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
     * Hero 5 more attackDamage
     *
     * @param WorldItemEntity the item thats collected
     * @param whoCollides the Hero who collects the item
     */
    @Override
    public void onCollect(Entity WorldItemEntity, Entity whoCollides) {
        if (defaultOnCollect(WorldItemEntity, whoCollides)) {
            DamageComponent damageComponent =
                (DamageComponent) whoCollides.getComponent(DamageComponent.class).get();
            damageComponent.setRangeDamage(damageComponent.getRangeDamage() + 5);
        }
    }

    @Override
    public void onDrop(Entity user, ItemData which, Point position) {}

    @Override
    public void onUse(Entity e, ItemData item) {}
}
