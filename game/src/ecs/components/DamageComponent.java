package ecs.components;

import ecs.entities.Entity;

/** Represents the damage an entity can deal */
public class DamageComponent extends Component {

    private int meleeDamage;
    private int rangeDamage;

    /**
     * Create a DamageComponent and add it to the associated entity Sets the melee and range damage
     * to 1 as default
     *
     * @param entity associated entity
     */
    public DamageComponent(Entity entity) {
        super(entity);
        meleeDamage = 1;
        rangeDamage = 1;
    }

    /**
     * Create a DamageComponent and add it to the associated entity
     *
     * @param entity associated entity
     * @param meleeDamage meleeDamage for the entity
     * @param rangeDamage rangeDamage for the entity
     */
    public DamageComponent(Entity entity, int meleeDamage, int rangeDamage) {
        super(entity);
        this.meleeDamage = meleeDamage;
        this.rangeDamage = rangeDamage;
    }

    /**
     * Gets the meleeDamage of the entity
     *
     * @return The meleeDamage of the entity
     */
    public int getMeleeDamage() {
        return meleeDamage;
    }

    /**
     * Sets the meleeDamage of the entity
     *
     * @param meleeDamage The meleeDamage of the entity
     */
    public void setMeleeDamage(int meleeDamage) {
        this.meleeDamage = meleeDamage;
    }

    /**
     * Sets the rangeDamage of the entity
     *
     * @return The rangeDamage of the entity
     */
    public int getRangeDamage() {
        return rangeDamage;
    }

    /**
     * Sets the rangeDamage of the entity
     *
     * @param rangeDamage The rangeDamage of the entity
     */
    public void setRangeDamage(int rangeDamage) {
        this.rangeDamage = rangeDamage;
    }
}
