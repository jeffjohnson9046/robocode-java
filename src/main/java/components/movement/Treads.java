package components.movement;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

/**
 * Methods for interacting with the {@link robocode.AdvancedRobot}'s movement mechanism for moving around the arena.
 */
public interface Treads {
    /**
     * Take action when hit by a bullet
     *
     * @param hitByBulletEvent The {@link robocode.HitByBulletEvent} arguments
     */
    void reactToBulletHit(HitByBulletEvent hitByBulletEvent);

    /**
     * Take action when colliding with a wall
     *
     * @param hitWallEvent The {@link robocode.HitWallEvent} arguments from the {@code OnHitWall} event.
     */
    void reactToWall(HitWallEvent hitWallEvent);

    /**
     * Take action when colliding with another robot
     *
     * @param hitRobotEvent The {@link robocode.HitRobotEvent} arguments from the {@code OnHitRobot} event.
     */
    void reactToRobotImpact(HitRobotEvent hitRobotEvent);

    /**
     * Take defensive action
     *
     * @param scannedRobotEvent The {@link robocode.ScannedRobotEvent} arguments from the {@code OnScannedRobot} event
     * @param currentEnemyEnergy The target's current energy level
     */
    void makeDefensiveMove(ScannedRobotEvent scannedRobotEvent, double currentEnemyEnergy);

    /**
     * Take offensive action
     */
    void makeOffensiveMove();
}
