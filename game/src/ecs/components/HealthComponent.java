package ecs.components;

import com.badlogic.gdx.utils.Null;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import graphic.Animation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import logging.CustomLogLevel;
import semanticAnalysis.types.DSLContextMember;
import semanticAnalysis.types.DSLType;
import semanticAnalysis.types.DSLTypeMember;

/** The HealthComponent makes an entity vulnerable and killable */
/** Die HealthComponent macht eine Entität verwundbar und tötbar */
@DSLType(name = "health_component")
public class HealthComponent extends Component {
    private static final List<String> missingTexture = List.of("animation/missingTexture.png");

    private final List<Damage> damageToGet;
    private @DSLTypeMember(name = "maximal_health_points") int maximalHealthpoints;
    private int currentHealthpoints;
    private @Null Entity lastCause = null;
    private @DSLTypeMember(name = "on_death_function") IOnDeathFunction onDeath;
    private @DSLTypeMember(name = "get_hit_animation") Animation getHitAnimation;
    private @DSLTypeMember(name = "die_animation") Animation dieAnimation;
    private final Logger healthLogger = Logger.getLogger(this.getClass().getName());

    /**
     * English:
     * Creates a new HealthComponent
     *
     * @param entity associated entity
     * @param maximalHitPoints maximum amount of hit-points, currentHitPoints can't be bigger than
     *     that
     * @param onDeath Function that gets called, when this entity dies
     * @param getHitAnimation Animation to be played as the entity was hit
     * @param dieAnimation Animation to be played as the entity dies
     */
    /**
     * German:
     * Erzeugt eine neue HealthComponent
     *
     * @param entity assoziierte Entität
     * @param maximalHitPoints maximale Anzahl von Trefferpunkten, currentHitPoints kann nicht größer sein als
     * das
     * @param onDeath Funktion, die aufgerufen wird, wenn diese Entität stirbt
     * @param getHitAnimation Animation, die abgespielt wird, wenn die Entität getroffen wurde
     * @param dieAnimation Animation, die abgespielt wird, wenn die Entität stirbt
     */
    public HealthComponent(
            Entity entity,
            int maximalHitPoints,
            IOnDeathFunction onDeath,
            Animation getHitAnimation,
            Animation dieAnimation) {
        super(entity);
        this.maximalHealthpoints = maximalHitPoints;
        this.currentHealthpoints = maximalHitPoints;
        this.onDeath = onDeath;
        this.getHitAnimation = getHitAnimation;
        this.dieAnimation = dieAnimation;
        damageToGet = new ArrayList<>();
    }

    /**
     * English:
     * Creates a HealthComponent with default values
     *
     * @param entity associated entity
     */
    /**
     * German:
     * Erzeugt eine HealthComponent mit Standardwerten
     *
     * @param entity assoziierte Entität
     */
    public HealthComponent(@DSLContextMember(name = "entity") Entity entity) {
        this(
                entity,
                1,
                entity2 -> {},
                new Animation(missingTexture, 100),
                new Animation(missingTexture, 100));
    }

    /**
     * English:
     * Adds damage, which is accounted for by the system
     *
     * @param damage Damage that should be inflicted
     */
    /**
     * German:
     * Fügt Schaden hinzu, der vom System berücksichtigt wird
     *
     * @param damage Schaden, der zugefügt werden soll
     */
    public void receiveHit(Damage damage) {
        damageToGet.add(damage);
        this.lastCause = damage.cause() != null ? damage.cause() : this.lastCause;
    }

    /** Triggers the onDeath Function */
    public void triggerOnDeath() {
        onDeath.onDeath(entity);
    }

    /**
     * English:
     * Calculate the amount of damage of a certain type
     *
     * @param dt Type of damage object that still need to be accounted for
     * @return Sum of all damage objects of type dt (default: 0)
     */
    /**
     * German:
     * Berechne die Höhe des Schadens einer bestimmten Art
     *
     * @param dt Typ des Schadensobjekts, das noch berücksichtigt werden muss
     * @return Summe aller Schadensobjekte vom Typ dt (Standard: 0)
     */
    public int getDamage(DamageType dt) {
        int damageSum =
                damageToGet.stream()
                        .filter(d -> d.damageType() == dt)
                        .mapToInt(Damage::damageAmount)
                        .sum();

        healthLogger.log(
                CustomLogLevel.DEBUG,
                this.getClass().getSimpleName()
                        + " processed damage for entity '"
                        + entity.getClass().getSimpleName()
                        + "': "
                        + damageSum);

        return damageSum;
    }

    /** Clear the damage list */
    public void clearDamage() {
        damageToGet.clear();
    }

    /**
     * English:
     * Sets the current life points, capped at the value of the maximum hit-points
     *
     * @param amount new amount of current health-points
     */
    /**
     * German:
     * Setzt die aktuellen Lebenspunkte, die auf den Wert der maximalen Trefferpunkte begrenzt sind.
     *
     * @param amount neue Anzahl der aktuellen Lebenspunkte
     */
    public void setCurrentHealthpoints(int amount) {
        this.currentHealthpoints = Math.min(maximalHealthpoints, amount);
    }

    /**
     * English:
     * Sets the value of the Maximum health-points. If the new maximum health-points are less than
     * the current health-points, the current points are set to the new maximum health-points.
     *
     * @param amount new amount of maximal health-points
     */
    /**
     * German:
     * Setzt den Wert für die maximalen Lebenspunkte. Wenn die neuen maximalen Lebenspunkte kleiner sind als
     * die aktuellen Gesundheitspunkte, werden die aktuellen Punkte auf die neuen maximalen Gesundheitspunkte gesetzt.
     *
     * @param amount neue Anzahl der maximalen Lebenspunkte
     */
    public void setMaximalHealthpoints(int amount) {
        this.maximalHealthpoints = amount;
        currentHealthpoints = Math.min(currentHealthpoints, maximalHealthpoints);
    }

    /**
     * English:
     * Set the animation to be played when the entity dies
     *
     * @param dieAnimation new dieAnimation
     */
    /**
     * German:
     * Legt die Animation fest, die abgespielt wird, wenn die Entität stirbt
     *
     * @param dieAnimation new dieAnimation
     */
    public void setDieAnimation(Animation dieAnimation) {
        this.dieAnimation = dieAnimation;
    }

    /**
     * German:
     * Set the animation to be played when the entity is hit
     *
     * @param isHitAnimation new isHitAnimation
     */
    /**
     * English:
     * Legt die Animation fest, die abgespielt wird, wenn das Objekt getroffen wird.
     *
     * @param isHitAnimation new isHitAnimation
     */
    public void setGetHitAnimation(Animation isHitAnimation) {
        this.getHitAnimation = isHitAnimation;
    }

    /**
     * English:
     * Set a new function to be called when dying.
     *
     * @param onDeath new onDeath function
     */
    /**
     * German:
     * Legt eine neue Funktion fest, die beim Sterben aufgerufen wird.
     *
     * @param onDeath neue onDeath-Funktion
     */
    public void setOnDeath(IOnDeathFunction onDeath) {
        this.onDeath = onDeath;
    }

    /**
     * @return The current health-points the entity has
     */
    public int getCurrentHealthpoints() {
        return currentHealthpoints;
    }

    /**
     * @return The maximal health-points the entity can have
     */
    public int getMaximalHealthpoints() {
        return maximalHealthpoints;
    }

    /**
     * @return Animation to be played as the entity was hit
     */
    public Animation getGetHitAnimation() {
        return getHitAnimation;
    }

    /**
     * @return Animation to be played when dying
     */
    public Animation getDieAnimation() {
        return dieAnimation;
    }

    /**
     * @return The last entity that caused damage to this entity.
     */
    public Optional<Entity> getLastDamageCause() {
        return Optional.ofNullable(this.lastCause);
    }
}
