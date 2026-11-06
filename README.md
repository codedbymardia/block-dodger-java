
# Block Dodger — Java Arcade Game

## Overview
Block Dodger is a desktop arcade game developed using Java Swing. The player controls a character that moves horizontally to avoid falling obstacles. The game becomes progressively harder over time.

## Features
- Keyboard-controlled player movement
- Randomly generated falling obstacles
- Increasing difficulty
- Collision detection
- Temporary invincibility power-ups
- Score tracking and persistent top-three high scores
- Start, game-over, and restart functionality

## Technologies
- Java
- Java Swing and AWT Graphics
- Object-Oriented Programming
- Java Collections Framework
- Java Serialization and File I/O

## Project Structure
- `Main.java` — Application entry point and game window
- `GamePanel.java` — Game loop, rendering, and player interactions
- `model/` — Player, Block, and PowerUp classes
- `io/FileHandler.java` — Score persistence
- `src/test/` — Included JUnit test source files

## Running the Application
Requires JDK 17 or newer.

From the repository root, run these commands in Windows CMD:

```cmd
mkdir out
javac -d out src\main\java\hu\bme\iit\blockdodger\model\*.java src\main\java\hu\bme\iit\blockdodger\io\*.java src\main\java\hu\bme\iit\blockdodger\*.java
java -cp out hu.bme.iit.blockdodger.Main
```

## Testing
JUnit test sources are included, but the existing FileHandler test requires updating to match the current FileHandler API. Automated test execution has not yet been configured.

## Academic Context
Developed as a university programming project at Budapest University of Technology and Economics (BME).
  
