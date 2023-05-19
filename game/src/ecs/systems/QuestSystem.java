package ecs.systems;

import ecs.quest.Quest;

import java.util.ArrayList;

public class QuestSystem extends ECS_System {

    private ArrayList<Quest> allQuests;

    @Override
    public void update() {
        allQuests = Quest.getAllQuests();
        for (Quest q:allQuests) {
            System.out.println(q.getName());
        }
    }
}
