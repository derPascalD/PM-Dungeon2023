package ecs.quest;

import java.util.ArrayList;
import java.util.logging.Logger;

/** Abstract class that helps to implement actual Quests */
public abstract class Quest {

    protected static ArrayList<Quest> allQuests = new ArrayList<>();

    protected String name;
    protected String description;
    protected String progressText;
    protected boolean accepted = false;
    /**
     * Creates a Quest with a name and a description
     *
     * @param name name of the Quest
     * @param description description of the Quest
     */
    protected Quest(String name, String description) {
        this.name = name;
        this.description = description;
        allQuests.add(this);
    }

    /**
     * Gets the progressText of the Quest as a String
     *
     * @return the progressText of the Quest
     */
    public String getProgress() {
        return progressText;
    }

    /** Updates the progressText */
    public abstract void updateProgress();

    /**
     * Checks if a Quest is completed
     *
     * @return true if quest is completed otherwise false
     */
    public abstract boolean isComplete();

    /** Gives the Hero his Reward */
    public abstract void onComplete();

    /** Logs when a Quest is completed */
    public void logCompletion() {
        Logger.getLogger(this.getClass().getName()).info(this.getName() + " Completed");
    }

    /**
     * Gets the Quest description
     *
     * @return Quest description as String
     */
    public String getDescription() {
        return description;
    }
    /**
     * Gets the Quests name
     *
     * @return Quests name as String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the description of the Quest
     *
     * @param description new Quest description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Sets the name of the Quest
     *
     * @param name new Quest name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets all the Quests that are in the game
     *
     * @return all the Quests that are in the game
     */
    public static ArrayList<Quest> getAllQuests() {
        return allQuests;
    }



    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
