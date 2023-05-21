package ecs.quest;

import ecs.components.InventoryComponent;
import ecs.entities.Hero;
import starter.Game;


public class LevelUpQuest extends Quest{

    private int questNumber = 8;

    /**
     * Creates a LevelU with its given name and description
     * @param name name of the quest
     * @param description description of the quest
     */
    public LevelUpQuest(String name, String description) {
        super(name, description);
        progressText = "0/"+ questNumber + " Dungeon Depth";
    }

    /**
     * Updates the progressText
     */
    @Override
    public void updateProgress() {
        progressText = Game.getLevelDepth() + "/"+ questNumber + " Dungeon Depth";
    }
    /**
     * Checks if the Hero has reached the certain level
     * @return true if the hero has reached the certain level otherwise false
     */
    @Override
    public boolean isComplete() {
        if(Game.getLevelDepth()>=questNumber) return true;
        return false;
    }

    /**
     * Gives the Hero more Inventory Space
     */
    @Override
    public void onComplete() {
        Hero hero = (Hero) Game.getHero().get();
        InventoryComponent heroInventory =
            (InventoryComponent) hero.getComponent(InventoryComponent.class).get();
        heroInventory.increaseInventorySize(3);
    }
}
