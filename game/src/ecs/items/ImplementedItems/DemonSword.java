package ecs.items.ImplementedItems;

import dslToGame.AnimationBuilder;
import ecs.components.DamageComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.*;
import starter.Game;
import tools.Point;

/** Gives the Hero more melee damage upon collecting */
public class DemonSword extends ItemData implements IOnCollect, IOnDrop, IOnUse {

    /** Creates a DemonSword Item which can be collected to get more melee damage */
    public DemonSword() {
        super(
                ItemType.Weapon,
                AnimationBuilder.buildAnimation("items.demonsword"),
                AnimationBuilder.buildAnimation("items.demonsword"),
                "DemonSword",
                "Gives the Hero 5 more melee damage");
        this.setOnCollect(this);
        this.setOnUse(this);
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
        Hero hero = (Hero) Game.getHero().get();
        DamageComponent heroDamage =
                (DamageComponent) hero.getComponent(DamageComponent.class).get();
        heroDamage.setMeleeDamage(heroDamage.getMeleeDamage() + 5);
    }

    @Override
    public void onDrop(Entity user, ItemData which, Point position) {}

    @Override
    public void onUse(Entity e, ItemData item) {}
}
