package ecs.quest;

import ecs.components.HealthComponent;
import ecs.entities.Hero;
import ecs.items.ImplementedItems.Healthpot;
import starter.Game;

public class HealQuest extends Quest {

    private int questNumber = 10;

    /**
     * Creates a HealQuest with its given name and description
     *
     * @param name name of the quest
     * @param description description of the quest
     */
    public HealQuest(String name, String description) {
        super(name, description);
        progressText = "0/" + questNumber + " Healthpots used";
    }

    /** Updates the progressText */
    @Override
    public void updateProgress() {
        progressText = Healthpot.getUseCount() + "/" + questNumber + " Healthpots used";
    }
    /**
     * Checks if the Hero has used a certain amount of Healpotions
     *
     * @return true if the Hero has used a certain amount of Healpotions otherwise false
     */
    @Override
    public boolean isComplete() {
        if (Healthpot.getUseCount() >= questNumber) return true;
        return false;
    }

    /** Gives the Hero more Healthpoints */
    @Override
    public void onComplete() {
        Hero hero = (Hero) Game.getHero().get();
        HealthComponent heroHealth =
                (HealthComponent) hero.getComponent(HealthComponent.class).get();
        heroHealth.setMaximalHealthpoints(heroHealth.getMaximalHealthpoints() + 10);
    }
}
