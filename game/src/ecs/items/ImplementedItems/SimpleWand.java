package ecs.items.ImplementedItems;

import dslToGame.AnimationBuilder;
import ecs.components.DamageComponent;
import ecs.components.InventoryComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.*;
import starter.Game;
import tools.Point;

public class SimpleWand extends ItemData implements IOnCollect, IOnDrop,IOnUse {



    public SimpleWand() {
        super(
            ItemType.Weapon,
            AnimationBuilder.buildAnimation("items.simplewand"),
            AnimationBuilder.buildAnimation("items.simplewand"),
            "SimpleWand",
            "Gives the Player more Attack Damage"
        );
        this.setOnCollect(this);
        this.setOnDrop(this);
        this.setOnUse(this);

        Entity worldItemEntity = WorldItemBuilder.buildWorldItem(this);
        new PositionComponent(worldItemEntity);
    }


    @Override
    public void onCollect(Entity WorldItemEntity, Entity whoCollides) {
        if(whoCollides instanceof Hero) {
            InventoryComponent inventoryCompnent =
                (InventoryComponent) whoCollides.getComponent(InventoryComponent.class).get();

            if (inventoryCompnent.getMaxSize() != inventoryCompnent.filledSlots()) {
                inventoryCompnent.addItem(this);
                DamageComponent damageComponent =
                    (DamageComponent) whoCollides.getComponent(DamageComponent.class).get();
                damageComponent.setAttackDamage(damageComponent.getAttackDamage()+5);
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
