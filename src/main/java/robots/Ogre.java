package robots;

import components.detection.Scanner;
import components.detection.impl.WidthLockScannerImpl;
import components.movement.Treads;
import components.movement.impl.DefensiveTreadsImpl;
import components.weaponry.Gun;
import components.weaponry.impl.OgreGun;
import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;


public class Ogre extends AdvancedRobot {
    private Scanner scanner;
    private Gun gun;
    private Treads treads;
    private double enemyPreviousEnergy;

    @Override
    public void run() {
        setColors(Color.DARK_GRAY, Color.LIGHT_GRAY, Color.BLACK, Color.PINK, Color.GREEN);

        scanner = new WidthLockScannerImpl(this);
        gun = new OgreGun(this);
        treads = new DefensiveTreadsImpl(this);

        while (true) {
            if (getRadarTurnRemaining() == 0D) {
                turnRadarRightRadians(Double.POSITIVE_INFINITY);
            }
            execute();
        }
    }

    @Override
    public void onScannedRobot(final ScannedRobotEvent event) {
        treads.makeDefensiveMove(event, enemyPreviousEnergy);

        scanner.scan(event);

        gun.aim(event);
        gun.fire(gun.getBulletPower(event));
    }

    @Override
    public void onHitByBullet(final HitByBulletEvent event) {
        treads.reactToBulletHit(event);
    }

    @Override
    public void onHitWall(final HitWallEvent event) {
        treads.reactToWall(event);
    }

    @Override
    public void onBulletHit(final BulletHitEvent event) {
        enemyPreviousEnergy = event.getEnergy();
    }

    @Override
    public void onHitRobot(final HitRobotEvent event) {
        enemyPreviousEnergy = event.getEnergy();
        treads.reactToRobotImpact(event);
    }
}
