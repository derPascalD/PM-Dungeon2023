package ecs.systems;

import ecs.quest.Quest;

/**
 * Class for handling Quests in the dungeon
 */
public class QuestSystem extends ECS_System {

    /**
     * Gets all Quests and checks if they are completed and execute their onComplete methods.
     * if a Quest is completed it also logs the completion of that Quest
     * Removes the Quest if its Completed
     */
    @Override
    public void update() {
        Quest.getAllQuests().stream()
            .filter(Quest::isComplete)
            .forEach(Quest::onComplete);

        Quest.getAllQuests().stream()
            .filter(Quest::isComplete)
            .forEach(Quest::logCompletion);

        Quest.getAllQuests().removeIf(Quest::isComplete);
    }
}
