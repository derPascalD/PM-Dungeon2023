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

    private static final List<String> healtpot = List.of("items/healthpot/Healthpots.png");
    private static final List<String> chestplate = List.of("items/chestplate/chestplate.png");
    private static final List<String> simplWand = List.of("items/simplewand/simplewand.png");


    private List<ItemData> templates =
            List.of(
                    new Healthpot(
                            ItemType.Healing,
                            new Animation(healtpot,1),
                            new Animation(healtpot,1),
                           // AnimationBuilder.buildAnimation(healtpot),
                            //AnimationBuilder.buildAnimation(healtpot),
                            "Healthpot",
                            "Heals the Player on Use"),
                    new SimpleWand(
                            ItemType.Weapon,
                            new Animation(simplWand,1),
                            new Animation(simplWand,1),
                           // AnimationBuilder.buildAnimation(simplWand),
                           //AnimationBuilder.buildAnimation(simplWand),
                            "SimpleWand",
                            "Gives the Player more Attack Damage"),
                    new Chestplate(
                            ItemType.Armor,
                            new Animation(chestplate,1),
                            new Animation(chestplate,1),
                            //// AnimationBuilder.buildAnimation(armor),
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
