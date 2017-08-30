package components.weaponry.impl;

import components.weaponry.Gun;
import robocode.AdvancedRobot;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import utils.Helpers;

public class OgreGun implements Gun {
    private final AdvancedRobot advancedRobot;

    public OgreGun(final AdvancedRobot advancedRobot) {
        this.advancedRobot = advancedRobot;
    }

    /**
     * Aim the gun at the target:
     *
     * 1.  Get the absolute bearing to the target.
     * 2.  Figure out the target's lateral velocity (i.e. how fast it's moving in parallel to us).
     * 3.  Determine how powerful the bullet should be (based on the target's range).
     * 4.  Figure out how fast the bullet will travel (according to Robocode: 20 - [MAX POWER] * power).
     * 5.  Determine the additional angle offset, so we can fire where the target will be, not where it is currently.
     *
     * @param scannedRobotEvent The {@link robocode.ScannedRobotEvent} arguments from the {@code OnScannedRobot} event
     */
    public void aim(final ScannedRobotEvent scannedRobotEvent) {
        final double absoluteBearing = Helpers.getAbsoluteBearing(advancedRobot.getHeadingRadians(),
            scannedRobotEvent.getBearingRadians());
        final double enemyLateralVelocity =
            scannedRobotEvent.getVelocity() * Math.sin(scannedRobotEvent.getHeadingRadians() - absoluteBearing);
        final double bulletPower = getBulletPower(scannedRobotEvent);
        final double bulletVelocity = Helpers.getBulletVelocity(bulletPower);
        final double angleOffset = Math.asin(enemyLateralVelocity / bulletVelocity);

        advancedRobot.turnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing - advancedRobot.getGunHeadingRadians() + angleOffset));
    }

    public void fire(final double bulletPower) {
        if (advancedRobot.getGunHeat() == 0D) {
            advancedRobot.setFire(bulletPower);
        }
    }

    /**
     * Determine how powerful the bullet should be.
     * <p>
     * Bullet power is based on distance to the target.
     * </p>
     *
     * @param scannedRobotEvent The {@link robocode.ScannedRobotEvent} arguments from the {@code OnScannedRobot} event
     * @return The power of the bullet
     */
    public double getBulletPower(final ScannedRobotEvent scannedRobotEvent) {
        return Math.min(400D / scannedRobotEvent.getDistance(), Rules.MAX_BULLET_POWER);
    }
}
