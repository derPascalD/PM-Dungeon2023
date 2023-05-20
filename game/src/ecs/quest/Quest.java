package ecs.quest;

import java.util.ArrayList;
import java.util.logging.Logger;

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

    public void logCompletion() {
        Logger.getLogger(this.getClass().getName()).info(this.getName() + " Completed");
    }

    public String getDescription() { return description; }

    public String getName() { return name; }

    public void setDescription(String description) { this.description = description; }

    public void setName(String name) { this.name = name; }

    public static ArrayList<Quest> getAllQuests() { return allQuests; }
}
