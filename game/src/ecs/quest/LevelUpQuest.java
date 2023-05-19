package ecs.quest;

public class LevelUpQuest extends Quest{

    public LevelUpQuest(String name, String description) {
        super(name, description);
    }

    @Override
    public String getProgress() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public void onComplete() {

    }
}
