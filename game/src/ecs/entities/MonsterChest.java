package ecs.entities;
import ecs.components.*;
import ecs.entities.Monsters.ChestMonster;
import ecs.items.ItemData;
import graphic.Animation;
import starter.Game;
import tools.Point;
import java.util.List;

/**
 * Chest which will be "dropped" at every LevelLoad. But on Use the Chest becomes an Monster
 */
public class MonsterChest extends Chest{
    public MonsterChest(List<ItemData> itemData, Point position) {
        super();
        new PositionComponent(this, position);
            InventoryComponent ic = new InventoryComponent(this, itemData.size());
            itemData.forEach(ic::addItem);
            new InteractionComponent(this, defaultInteractionRadius, false,this::spawnChestMonster );
            AnimationComponent ac =
                new AnimationComponent(
                    this,
                    new Animation(DEFAULT_CLOSED_ANIMATION_FRAMES, 50, false),
                    new Animation(DEFAULT_OPENING_ANIMATION_FRAMES, 50, false));
    }

    /**
     * let spawn a monster by interacting with the chest
     * @param entity - chest
     */
    public void spawnChestMonster(Entity entity) {
        new ChestMonster(Game.getLevelDepth(), (Chest)entity);
        Game.removeEntity(entity);
    }
}
