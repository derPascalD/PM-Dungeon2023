package starter;

import ecs.components.InventoryComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.Monsters.Monster;

import ecs.entities.Traps.Trap;
import ecs.items.ImplementedItems.*;
import ecs.items.ItemData;
import ecs.items.ItemType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveGame {

    SaveData saveDataRead;

    /**
     * Game Instance which data is to be managed
     */
    public SaveGame() {
    }

    public void writeSave() {

        // Data Class
        SaveData saveData = new SaveData();

        //  Level Depth
        saveData.setLevelDepth(Game.getLevelDepth());

        //Entities
        ArrayList<Entity> entities = new ArrayList<>(Game.getEntitiesToAdd());
        entities.removeIf(e -> e instanceof Hero);

        // Level Size
        saveData.setLEVELSIZE(Game.getLevelSize());

        // Save Hero
        Hero hero = (Hero) Game.getHero().get();
        saveData.setHero(hero);


        // Hero Stats
        for (Entity h : Game.getEntities()) {
            if (h instanceof Hero) {

                // Save Hero Level
                saveData.setLevelHero((int) ((Hero) h).getXpComponent().getCurrentLevel());

                // Save Hero xp
                saveData.setXpPointsHero((int) ((Hero) h).getXpComponent().getCurrentXP());

                // Save Hero Max HP
                saveData.setMaxHPHero(((Hero) h).getHealthComponent().getMaximalHealthpoints());

                // Inventory
                ArrayList<String> inventory = new ArrayList<>();
                InventoryComponent inventoryComponent =
                    (InventoryComponent) h.getComponent(InventoryComponent.class).get();
                for (ItemData items: inventoryComponent.getItems()) {
                    inventory.add(items.getItemName());
                }
                saveData.setInventory(inventory);

                // Bag Inventory
                ArrayList<String> bagInventory = new ArrayList<>();
                for (ItemData items: inventoryComponent.getItems()) {
                    if(items instanceof Bag)
                    for (int i = 0; i < ((Bag) items).getBag().size(); i++) {
                        bagInventory.add(String.valueOf(((Bag) items).getBag().get(i)));
                        System.out.println("Bag Inhalte: " + ((Bag) items).getBag().get(i));
                    }
                }saveData.setBagInventory(bagInventory);


            }
        }




        saveData.setListEntity(entities);
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        try {
            fileOutputStream = new FileOutputStream("SaveGame.ser");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(saveData);
            objectOutputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void readSave() {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        saveDataRead = new SaveData();


        try {
            fileInputStream = new FileInputStream("SaveGame.ser");
            objectInputStream = new ObjectInputStream(fileInputStream);
            saveDataRead = (SaveData) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Game.setLevelSize(saveDataRead.getLEVELSIZE());

    }

    public void returnEntities() {
        Game.setLevelDepth(saveDataRead.getLevelDepth());
        for (Entity entity : saveDataRead.getListEntity()) {
            entity.components = new HashMap<>();
            Game.addEntity(entity);
            if (entity instanceof Monster) ((Monster) entity).setup(saveDataRead.getLevelDepth());
            if (entity instanceof Trap) ((Trap) entity).setup();
        }

    }

    public void returnXPLevel(Hero hero) {
        hero.getXpComponent().setCurrentXP(saveDataRead.getXpPointsHero());
        hero.getXpComponent().setCurrentLevel(saveDataRead.getLevelHero());
        hero.getHealthComponent().setMaximalHealthpoints(saveDataRead.getMaxHPHero());
        hero.getHealthComponent().setCurrentHealthpoints(saveDataRead.getMaxHPHero());
        hero.onLevelUp(saveDataRead.getLevelHero());

    }

    public void loadInventory(Hero hero){
        InventoryComponent inventoryComponent =
            (InventoryComponent) hero.getComponent(InventoryComponent.class).get();


        for (String items: saveDataRead.getInventory()) {
            switch (items){
                case "Healthpot" -> inventoryComponent.addItem(new Healthpot());
                case "Bag" -> inventoryComponent.addItem(new Bag(ItemType.Healing));
                case "Chestplate" -> inventoryComponent.addItem(new Chestplate());
                case  "SimpleWand" -> inventoryComponent.addItem(new SimpleWand());
                case "DemonSword" -> inventoryComponent.addItem(new DemonSword());
            }
        }
    }
}
