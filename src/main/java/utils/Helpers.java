package utils;

import robocode.Rules;

public class Helpers {
    private static final double MAX_BULLET_VELOCITY = 20D;
    public static final double ROBOT_WIDTH = 36D;
    public static final double NINETY_DEGREES_IN_RADIANS = Math.PI / 2D;

    /**
     * Determine a target's absolute bearing to this {@link robocode.AdvancedRobot}.
     *
     * @param myHeading This {@link robocode.AdvancedRobot}'s heading
     * @param enemyBearing The enemy {@link robocode.AdvancedRobot}'s bearhing
     * @return The enemy {@link robocode.AdvancedRobot}'s absolute bearing
     */
    public static double getAbsoluteBearing(final double myHeading, final double enemyBearing) {
        return myHeading + enemyBearing;
    }

    /**
     * Calculate the {@link robocode.Bullet}'s velocity based on its power.
     *
     * @param bulletPower The power/strength of the {@link robocode.Bullet}
     * @return The {@link robocode.Bullet}'s velocity
     */
    public static double getBulletVelocity(final double bulletPower) {
        return MAX_BULLET_VELOCITY - (Rules.MAX_BULLET_POWER * bulletPower);
    }
}
