# Ogre - A Robocode Robot

## Overview
This [Robocode](http://robocode.sourceforge.net/) robot is a port of the robot originally written in .NET to participate in Fairway's Robocode tournament.

Loosely based on the [Ogre](https://boardgamegeek.com/boardgame/5206/ogre) board game, the code is broken down into two main packages:

* **components:** The parts that make up the Ogre robot:
  * **detection:** Controls the Ogre's radar and 
  * **movement:** Controls the Ogre's movement around the arena
  * **weaponry:** Controls the Ogre's turret and gun
* **robots:**  The robot implementation that extends `Robot` or `AdvancedRobot`

## Additional References
* [Robocode Wiki](http://robowiki.net/)