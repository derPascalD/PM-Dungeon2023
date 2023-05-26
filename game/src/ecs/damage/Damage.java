package ecs.damage;

import com.badlogic.gdx.utils.Null;
import ecs.entities.Entity;

/**
 * English: Damage that can reduce the life points of an entity
 *
 * @param damageAmount Number of life points to be deducted. Value before taking into account
 *     resistances and vulnerabilities.
 * @param damageType Type of damage, this is important for accounting the actual damage taking into
 *     account resistances or vulnerabilities
 * @param cause Entity that caused the damage (e.g. the player) can be null
 */
/**
 * German: Schaden, der die Lebenspunkte einer Entität reduzieren kann
 *
 * @param damageAmount Anzahl der Lebenspunkte, die abgezogen werden. Wert vor Berücksichtigung von
 *     Resistenzen und Verwundbarkeiten.
 * @param damageType Art des Schadens, dies ist wichtig für die Berechnung des tatsächlichen
 *     Schadens unter Berücksichtigung von Berücksichtigung von Resistenzen oder Verwundbarkeiten
 * @param cause Entität, die den Schaden verursacht hat (z.B. der Spieler), kann null sein
 */
public record Damage(int damageAmount, DamageType damageType, @Null Entity cause) {}
