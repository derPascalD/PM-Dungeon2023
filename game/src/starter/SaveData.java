package starter;

import ecs.components.InventoryComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.ItemData;
import level.tools.LevelSize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveData implements Serializable {

    private ArrayList<Entity> listEntity;

    private int levelDepth;

    private int maxHPHero;
    private int levelHero;
    private int xpPointsHero;
    private LevelSize LEVELSIZE;

    private Hero hero;

    private ArrayList<String> inventory;
    private ArrayList<String> bagInventory;


    public ArrayList<Entity> getListEntity() {
        return listEntity;
    }

    public void setListEntity(ArrayList<Entity> listEntity) {
        this.listEntity = listEntity;
    }

    public int getLevelDepth() {
        return levelDepth;
    }

    public void setLevelDepth(int levelDepth) {
        this.levelDepth = levelDepth;
    }

    public int getMaxHPHero() {
        return maxHPHero;
    }

    public void setMaxHPHero(int maxHPHero) {
        this.maxHPHero = maxHPHero;
    }

    public int getLevelHero() {
        return levelHero;
    }

    public void setLevelHero(int levelHero) {
        this.levelHero = levelHero;
    }

    public int getXpPointsHero() {
        return xpPointsHero;
    }

    public void setXpPointsHero(int xpPointsHero) {
        this.xpPointsHero = xpPointsHero;
    }

    public LevelSize getLEVELSIZE() {
        return LEVELSIZE;
    }

    public void setLEVELSIZE(LevelSize LEVELSIZE) {
        this.LEVELSIZE = LEVELSIZE;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<String> getBagInventory() {
        return bagInventory;
    }

    public void setBagInventory(ArrayList<String> bagInventory) {
        this.bagInventory = bagInventory;
    }
}
