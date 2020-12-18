package FastAndStupid.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerCar {

    // load models of player car
    protected Image imgPlayerStandardCar= new ImageIcon(getClass().getResource("/img/playerCar/car3.png")).getImage();
    protected Image imgPlayerLeftCar= new ImageIcon(getClass().getResource("/img/playerCar/carLeft3.png")).getImage();
    protected Image imgPlayerRightCar= new ImageIcon(getClass().getResource("/img/playerCar/carRight3.png")).getImage();
    protected Image imgPlayerSpeedUpCar= new ImageIcon(getClass().getResource("/img/playerCar/carFast3.png")).getImage();
    protected Image imgPlayerSpeedDownCar= new ImageIcon(getClass().getResource("/img/playerCar/carStop3.png")).getImage();
    protected Image imgPlayer= imgPlayerStandardCar;
    private boolean checkSpeedUp = false;       // for prevent endless addition speed when button is pressed
    protected double fullWay = 0;               // counter for all distance
    int speedPlayer = Settings.minSpeedCar;     // start speed player
    int x = Settings.defaultCarPositionX;       // start position
    int y = Settings.defaultCarPositionY;
    int layer1 = 0;                             // position of layers
    int layer2 = -(Settings.windowHeight);

    // for check collisions between objects
    public Rectangle getRectangle() { return new Rectangle(x+40,y+Settings.lightsOnPlayerCar,imgPlayer.getWidth(null)-40,(imgPlayer.getHeight(null)-Settings.lightsOnPlayerCar)); }

    public void move()  // move car on layer
    {
        double speed = speedPlayer;
        fullWay+=  speed/7000;
        if(layer2 + speedPlayer >= 0)
        {
            layer1 = 0;
            layer2 = -(Settings.windowHeight);
        }
        layer1 += speedPlayer;
        layer2 += speedPlayer;
        System.out.println(fullWay);
    }

    public void keyPressed(KeyEvent event) {    // when control buttons is pressed
        if (KeyEvent.VK_A ==event.getKeyCode() && Settings.roadLeftBottom<x-Settings.moveCar || KeyEvent.VK_LEFT ==event.getKeyCode() && Settings.roadLeftBottom<x-Settings.moveCar)
        {
            imgPlayer= imgPlayerLeftCar;
            x-=Settings.moveCar;
        }
        if (KeyEvent.VK_D ==event.getKeyCode() && Settings.roadRightBottom>x+Settings.moveCar || KeyEvent.VK_RIGHT ==event.getKeyCode() && Settings.roadRightBottom>x+Settings.moveCar)
        {
            imgPlayer= imgPlayerRightCar;
            x+=Settings.moveCar;
        }
        if (KeyEvent.VK_W ==event.getKeyCode() && Settings.topSpeedCar>speedPlayer+1 || KeyEvent.VK_UP ==event.getKeyCode() && Settings.topSpeedCar>speedPlayer+1)
        {
            if (!checkSpeedUp)y-=20;
            speedPlayer++;
            checkSpeedUp=true;
            imgPlayer = imgPlayerSpeedUpCar;
        }
        if (KeyEvent.VK_S ==event.getKeyCode() && Settings.minSpeedCar<speedPlayer-1 || KeyEvent.VK_DOWN ==event.getKeyCode() && Settings.minSpeedCar<speedPlayer-1)
        {
            speedPlayer--;
            imgPlayer = imgPlayerSpeedDownCar;
        }
    }

    public void keyReleased(KeyEvent event) { // when control buttons is released
        imgPlayer= imgPlayerStandardCar;
        if (checkSpeedUp)
        {
            y+=20;
            checkSpeedUp =false;
        }
    }
}
