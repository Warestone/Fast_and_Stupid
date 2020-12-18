package FastAndStupid.Client;

public class Settings {
    public static int windowWidth = 1033;             // game window settings (apply with background.png resolution)
    public static int windowHeight = 896;
    public static int moveCar = 20;                   // step for turn car
    public static int roadLeftBottom = 165;           // road restrictions for car
    public static int roadRightBottom = 605;
    public static int topSpeedCar = 50;
    public static int minSpeedCar = 10;
    public static int defaultCarPositionX = 383;      // start car position
    public static int defaultCarPositionY = 480;
    public static int timerDelay = 20;                // affect to car speed
    public static int maxPoliceCarSpeed = 30;         // max speed of police car
    public static int maxDelaySpawnPoliceCar = 3000;  // max delay for spawn police car (fact delay is random(max))
    public static int policeSoundsDelay = 20000;      // delay for playing police radio sounds
    public static int lightsOnPlayerCar = 140;        // for subtract lights car size in collision model (0, if car haven't lights)
    public static int livesPlayerCar = 3;             // quantity lives of player
    public static int godMode=0;                      // god mode - endless lives. 0-disabled
}
