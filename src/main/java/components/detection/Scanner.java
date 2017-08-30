package components.detection;

import robocode.ScannedRobotEvent;

/**
 * Methods for interacting with the {@link robocode.AdvancedRobot}'s radar.
 */
public interface Scanner {
    /**
     * Attempt to detect an enemy robot
     * @param scannedRobotEvent The {@link robocode.ScannedRobotEvent} arguments from the {@code OnScannedRobot} event
     */
    void scan(ScannedRobotEvent scannedRobotEvent);
}
