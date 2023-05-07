package ecs.items;

import ecs.components.PositionComponent;

public abstract class Item implements IOnCollect,IOnUse,IOnDrop {

    protected ItemData itemData;

    protected Item(ItemData itemData) {
        this.itemData = itemData;
        new PositionComponent(WorldItemBuilder.buildWorldItem(itemData));
    }

}
