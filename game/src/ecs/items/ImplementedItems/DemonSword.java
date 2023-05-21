package ecs.items.ImplementedItems;

import dslToGame.AnimationBuilder;
import ecs.components.DamageComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.*;
import starter.Game;
import tools.Point;

public class DemonSword extends ItemData implements IOnCollect, IOnDrop, IOnUse {

    /**
     * Creates a DemonSword Item which can be collected to get more melee damage
     */
    public DemonSword() {
        super(
            ItemType.Weapon,
            AnimationBuilder.buildAnimation("items.bag"),
            AnimationBuilder.buildAnimation("items.bag"),
            "DemonSword",
            "Gives the Hero 5 more melee damage");
        this.setOnCollect(this);
        this.setOnDrop(this);
        this.setOnUse(this);
    }

    @Override
    public void onCollect(Entity WorldItemEntity, Entity whoCollides) {
        defaultOnCollect(WorldItemEntity, whoCollides);
        Hero hero = ( Hero ) Game.getHero().get();
        DamageComponent heroDamage =
            (DamageComponent) hero.getComponent(DamageComponent.class).get();
        heroDamage.setMeleeDamage(heroDamage.getMeleeDamage()+5);
    }

    @Override
    public void onDrop(Entity user, ItemData which, Point position) {

    }

    @Override
    public void onUse(Entity e, ItemData item) {

    }
}
