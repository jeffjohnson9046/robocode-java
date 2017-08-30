package components.weaponry;

import robocode.ScannedRobotEvent;

/**
 * Methods for interacting with the {@link robocode.AdvancedRobot}'s turret to aim and fire its weapon.
 */
public interface Gun {
    /**
     * Aim the robot's gun at the target.
     *
     * @param scannedRobotEvent  The {@link robocode.ScannedRobotEvent} arguments from the {@code OnScannedRobot} event
     */
    void aim(ScannedRobotEvent scannedRobotEvent);

    /**
     * Fire a bullet at the target.
     *
     * @param bulletPower The strength of the bullet to shoot
     */
    void fire(double bulletPower);

    /**
     * Determine how powerful the bullet should be.
     *
     * @param scannedRobotEvent  The {@link robocode.ScannedRobotEvent} arguments from the {@code OnScannedRobot} event
     * @return The power of the bullet
     */
    double getBulletPower(ScannedRobotEvent scannedRobotEvent);
}
