package ecs.quest;

import ecs.components.InventoryComponent;
import ecs.entities.Hero;
import starter.Game;

import java.util.logging.Logger;

public class LevelUpQuest extends Quest{

    public LevelUpQuest(String name, String description) {
        super(name, description);
    }

    @Override
    public String getProgress() {
        return "Your currently at " + Game.getLevelDepth() + "/8 Dungeon depth";
    }

    @Override
    public boolean isComplete() {
        if(Game.getLevelDepth()>=2) return true;
        return false;
    }

    @Override
    public void onComplete() {
        Hero hero = (Hero) Game.getHero().get();
        InventoryComponent heroInventory =
            (InventoryComponent) hero.getComponent(InventoryComponent.class).get();
        heroInventory.increaseInventorySize(3);
    }
}
