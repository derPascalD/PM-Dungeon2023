package ecs.items;

import dslToGame.AnimationBuilder;
import ecs.items.ImplementedItems.Chestplate;
import ecs.items.ImplementedItems.Healthpot;
import ecs.items.ImplementedItems.SimpleWand;
import graphic.Animation;
import java.util.List;
import java.util.Random;

/** Generator which creates a random ItemData based on the Templates prepared. */
public class ItemDataGenerator {
    private static final List<String> missingTexture = List.of("animation/missingTexture.png");

    private List<ItemData> templates =
            List.of(
                    new Healthpot(
                        ItemType.Healing,
                        AnimationBuilder.buildAnimation("items.healthpot"),
                        AnimationBuilder.buildAnimation("items.healthpot"),
                        "Healthpot",
                        "Heals the Player on Use"),
                    new SimpleWand(
                        ItemType.Weapon,
                        AnimationBuilder.buildAnimation("items.simplewand"),
                        AnimationBuilder.buildAnimation("items.simplewand"),
                        "SimpleWand",
                        "Gives the Player more Attack Damage"),
                    new Chestplate(
                        ItemType.Armor,
                        AnimationBuilder.buildAnimation("items.chestplate"),
                        AnimationBuilder.buildAnimation("items.chestplate"),
                        "Chestplate",
                        "Protects the Player"));
    private Random rand = new Random();

    /**
     * @return a new randomItemData
     */
    public ItemData generateItemData() {
        return templates.get(rand.nextInt(templates.size()));
    }
}
