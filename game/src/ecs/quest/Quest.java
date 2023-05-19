package ecs.quest;

import java.util.ArrayList;

public abstract class Quest {

    protected static ArrayList<Quest> allQuests = new ArrayList<>();

    protected String name;
    protected String description;

    protected Quest(String name, String description) {
        this.name = name;
        this.description = description;
        allQuests.add(this);
    }


    public abstract String getProgress();
    public abstract boolean isComplete();
    public abstract void onComplete();

    public String getDescription() { return description; }

    public String getName() { return name; }

    public void setDescription(String description) { this.description = description; }

    public void setName(String name) { this.name = name; }

    public static ArrayList<Quest> getAllQuests() { return allQuests; }
}
