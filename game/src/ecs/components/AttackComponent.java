package ecs.components;

import ecs.entities.Entity;
import graphic.Animation;
import semanticAnalysis.types.DSLType;
import semanticAnalysis.types.DSLTypeMember;

import java.util.List;

/**
 * English:
 * AnimationComponent is a component that stores the possible animations and the current animation
 * of an entity
 */
/**
 * German:
 * AnimationComponent ist eine Komponente, die die möglichen Animationen und die aktuelle Animation
 * einer Entität
 */
@DSLType(name = "animation_attack")
public class AttackComponent extends Component {
    private static List<String> missingTexture = List.of("animation/missingTexture.png");
    private @DSLTypeMember(name = "idle_left") Animation idleLeft;
    private @DSLTypeMember(name = "idle_right") Animation idleRight;
    private @DSLTypeMember(name = "current_animation") Animation currentAnimation;

    /**
     * English:
     * Create a new component and add it to the associated entity
     *
     * @param entity associated entity
     */
    /**
     * @param entity associated entity
     * @param idleLeft Idleanimation faced left
     * @param idleRight Idleanimation faced right
     */
    /**
     * German:
     * Erstellt eine neue Komponente und fügt sie der zugehörigen Entität hinzu
     *
     * @param entity assoziierte Entität
     */
    /**
     * @param entity assoziierte Entität
     * @param idleLeft Idleanimation nach links gerichtet
     * @param idleRight Idleanimation mit Blick nach rechts
     */
    public AttackComponent(Entity entity, Animation idleLeft, Animation idleRight) {
        super(entity);
        this.idleRight = idleRight;
        this.idleLeft = idleLeft;
        this.currentAnimation = idleLeft;
    }
}
