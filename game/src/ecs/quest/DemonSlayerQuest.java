package ecs.quest;

import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.Monsters.Demon;
import ecs.items.ImplementedItems.DemonSword;
import ecs.items.WorldItemBuilder;
import starter.Game;

public class DemonSlayerQuest extends Quest {

    private int questNumber = 10;

    /**
     * Creates a DemonSlayerQuest with its given name and description
     *
     * @param name name of the quest
     * @param description description of the quest
     */
    public DemonSlayerQuest(String name, String description) {
        super(name, description);
        progressText = "0/" + questNumber + " Demons slain";
    }

    /** Updates the progressText */
    @Override
    public void updateProgress() {
        progressText = countDemonsSlayn() + "/" + questNumber + " Demons slain";
    }

    /**
     * Checks if the Hero has slain enough demons
     *
     * @return true if the hero has slain all the needed demons otherwise false
     */
    @Override
    public boolean isComplete() {
        if (countDemonsSlayn() >= questNumber) return true;
        return false;
    }

    /** Spawns a DemonSword Item where the Hero is */
    @Override
    public void onComplete() {
        Hero hero = (Hero) Game.getHero().get();
        Entity demonSword = WorldItemBuilder.buildWorldItem(new DemonSword());

        PositionComponent heroPosition =
                (PositionComponent) hero.getComponent(PositionComponent.class).get();
        new PositionComponent(demonSword, heroPosition.getPosition());
    }
    // Counts how many Demons were slain
    private long countDemonsSlayn() {
        Hero hero = (Hero) Game.getHero().get();
        return hero.getKilledMonsters().stream().filter(entity -> entity instanceof Demon).count();
    }
}
