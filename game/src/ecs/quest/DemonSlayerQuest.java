package ecs.quest;

import ecs.components.InventoryComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.Monsters.Demon;
import ecs.items.ImplementedItems.DemonSword;
import ecs.items.WorldItemBuilder;
import starter.Game;

public class DemonSlayerQuest extends Quest {

    private int questNumber = 10;

    public DemonSlayerQuest(String name, String description) {
        super(name, description);
        progressText =  "0/" + questNumber+ " Demons slain";
    }

    @Override
    public String getProgress() {
        return progressText;
    }

    @Override
    public void updateProgress() {
        progressText = countDemonsSlayn() + "/" + questNumber+ " Demons slain";
    }

    @Override
    public boolean isComplete() {
        if(countDemonsSlayn() >= questNumber) return true;
        return  false;
    }

    @Override
    public void onComplete() {
        Hero hero = ( Hero ) Game.getHero().get();
        InventoryComponent heroInventory =
            (InventoryComponent) hero.getComponent(InventoryComponent.class).get();
        Entity demonSword = WorldItemBuilder.buildWorldItem(new DemonSword());

        PositionComponent heroPosition =
            (PositionComponent) hero.getComponent(PositionComponent.class).get();
        new PositionComponent(demonSword,heroPosition.getPosition());
    }

    private long countDemonsSlayn() {
        Hero hero = ( Hero ) Game.getHero().get();
        return hero.getKilledMonsters().stream()
            .filter(entity -> entity instanceof Demon)
            .count();
    }
}
