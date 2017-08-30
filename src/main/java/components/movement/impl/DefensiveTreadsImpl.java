package components.movement.impl;

import components.movement.Treads;
import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.Helpers;

/**
 * Move defensively, reacting to attacks/damage
 */
public class DefensiveTreadsImpl implements Treads {
    private final AdvancedRobot advancedRobot;
    private double movementMultiplier = 1D;

    public DefensiveTreadsImpl(final AdvancedRobot advancedRobot) {
        this.advancedRobot = advancedRobot;
    }

    /**
     * When hit by a bullet, try to turn ninety degrees from the origin's bearing and move ahead.
     *
     * @param hitByBulletEvent The {@link robocode.HitByBulletEvent} arguments
     */
    public void reactToBulletHit(final HitByBulletEvent hitByBulletEvent) {
        final double enemyAbsoluteBearing = Helpers.getAbsoluteBearing(advancedRobot.getHeadingRadians(),
            hitByBulletEvent.getBearingRadians());
        final double turnAmountInRadians = getTurnRadians(enemyAbsoluteBearing);

        turn(enemyAbsoluteBearing, turnAmountInRadians);

        advancedRobot.setAhead(100D * movementMultiplier);
    }

    /**
     * After running into a wall, reverse direction and try to turn away from it.
     *
     * @param hitWallEvent The {@link robocode.HitWallEvent} arguments from the {@code OnHitWall} event.
     */
    public void reactToWall(final HitWallEvent hitWallEvent) {
        reverseDirection();

        advancedRobot.setAhead(100D * movementMultiplier);
    }

    /**
     * Move away from a robot after colliding with it
     *
     * @param hitRobotEvent The {@link robocode.HitRobotEvent} arguments from the {@code OnHitRobot} event.
     */
    public void reactToRobotImpact(final HitRobotEvent hitRobotEvent) {
        if (hitRobotEvent.isMyFault()) {
            reverseDirection();
        }

        advancedRobot.setAhead(150D * movementMultiplier);
    }

    /**
     * Attempt to dodge an incoming bullet:
     *
     * 1.  Get the absolute bearing of the enemy robot.
     * 2.  Determine if the enemy robot's energy has dropped.
     * 3.  Attempt to turn perpendicular to the target and move.
     *
     * <p>
     *     This works by detecting an enemy's change in {@code Energy} level.  If the enemy's {@code Energy} level
     *     changes, odds are he's fired a bullet at us.  There are several other conditions that might cause an enemy
     *     robot's energy level to drop (e.g. being hit by one of our bullets or hitting a wall), and this defensive
     *     move strategy will react to them all.  Using some other events (<c>OnBulletHit</c> or <c>OnHitRobot</c>), we
     *     can determine a few conditions when the enemy's energy was changed.  However, it's better to be safe than
     *     sorry.
     * </p>
     *
     * @param scannedRobotEvent The {@link robocode.ScannedRobotEvent} arguments from the {@code OnScannedRobot} event
     * @param currentEnemyEnergy The target's current energy level
     */
    public void makeDefensiveMove(final ScannedRobotEvent scannedRobotEvent, final double currentEnemyEnergy) {
        final double enemyAbsoluteBearing = Helpers.getAbsoluteBearing(advancedRobot.getHeadingRadians(),
            scannedRobotEvent.getBearingRadians());
        final double energyDiff = currentEnemyEnergy - scannedRobotEvent.getEnergy();

        if (advancedRobot.getDistanceRemaining() == 0D && energyDiff > 0D) {
            final double turnAmountInRadians = getTurnRadians(enemyAbsoluteBearing);
            turn(enemyAbsoluteBearing, turnAmountInRadians);
        }

        advancedRobot.setAhead(movementMultiplier * Helpers.ROBOT_WIDTH * Math.abs(energyDiff));
    }

    public void makeOffensiveMove() {
        throw new NotImplementedException();
    }

    /**
     * Turn this robot the specified number of radians.
     *
     * @param enemyAbsoluteBearing The enemy's absolute bearing to this robot
     * @param turnRadians The number of radians to turn the robot
     */
    private void turn(final double enemyAbsoluteBearing, final double turnRadians) {
        if (enemyAbsoluteBearing < 0D) {
            advancedRobot.setTurnRightRadians(turnRadians);
        } else {
            advancedRobot.setTurnLeftRadians(turnRadians);
        }
    }

    /**
     * Get the value to attempt turning this robot 90 degrees from the target.
     *
     * @param bearingRadians The enemy's bearing in radians
     * @return The number of radians required to turn this robot 90 degrees from the target
     */
    private static double getTurnRadians(final double bearingRadians) {
        return bearingRadians + Helpers.NINETY_DEGREES_IN_RADIANS;
    }

    /**
     * Set the components.movement velocity to the opposite direction.
     */
    private void reverseDirection() {
        movementMultiplier *= -1D;
    }
}
