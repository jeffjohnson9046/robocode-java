package components.detection.impl;

import components.detection.Scanner;
import robocode.AdvancedRobot;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import utils.Helpers;

/**
 * A {@link components.detection.Scanner} that implements the "Width Lock Scanner" on the Robocode wiki.
 */
public class WidthLockScannerImpl implements Scanner {
    private final AdvancedRobot advancedRobot;

    public WidthLockScannerImpl(final AdvancedRobot advancedRobot) {
        this.advancedRobot = advancedRobot;
    }

    /**
     * Figure out how far to turn the robot's radar, making sure the radar stays "locked" on the target.  To do that, we
     *
     * 1.  Calculate the target's absolute bearing.
     * 2.  Determine how far the radar has to turn in order to stay locked on our target.
     * 3.  Add in some padding (the "extra turn") in an attempt to account for the target's components.movement.
     *
     * @param scannedRobotEvent The {@link robocode.ScannedRobotEvent} arguments from the {@code onScannedRobot} event
     */
    public void scan(final ScannedRobotEvent scannedRobotEvent) {
        final double enemyAbsoluteBearing = Helpers.getAbsoluteBearing(advancedRobot.getHeadingRadians(),
            scannedRobotEvent.getBearingRadians());
        final double extraTurnAmount = Math.min(Math.atan(Helpers.ROBOT_WIDTH / scannedRobotEvent.getDistance()),
            Rules.RADAR_TURN_RATE_RADIANS);
        double radarTurnAmount = Utils.normalRelativeAngle(enemyAbsoluteBearing - advancedRobot.getRadarHeadingRadians());

        // if radarTurn is negative, then it's turning to the left, so we'll need to push it more to the left.
        // Otherwise, keep turning it to the right.
        radarTurnAmount += radarTurnAmount < 0D
            ? -extraTurnAmount
            : extraTurnAmount;

        advancedRobot.setTurnRadarRightRadians(radarTurnAmount);
    }
}
