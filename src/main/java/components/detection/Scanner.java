package components.detection;

import robocode.ScannedRobotEvent;

/**
 * Methods for interacting with the {@link robocode.AdvancedRobot}'s radar.
 */
public interface Scanner {
    void scan(ScannedRobotEvent e);
}
