package FastAndStupid.Client;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

public class PoliceCar {            // same as playerCar but simplified
    int x,y,speedPolice;
    protected Image imgPolice;
    protected StringBuilder nameImage = new StringBuilder("");
    Road road;

    public Rectangle getRectangle() { return new Rectangle(x,y,imgPolice.getWidth(null),imgPolice.getHeight(null)); }

    public PoliceCar(int x,int y,int speedPolice,Road road)
    {
        this.x = x;
        this.y = y;
        this.road = road;
        this.speedPolice = speedPolice;
        try
        {
            File imgDirPolice = new File(getClass().getResource("/img/policeCar").getFile());
            File[] imgPoliceCars = imgDirPolice.listFiles();

            if (imgPoliceCars!=null)
            {
                File image = imgPoliceCars[(int) (Math.random() * imgPoliceCars.length)];
                imgPolice= new ImageIcon(image.toURI().toURL()).getImage();
                nameImage.append(image.getName());
            }
            else imgPolice = new ImageIcon(getClass().getResource("/img/policeCar/police1.png")).getImage();
        }
        catch (MalformedURLException e) { e.printStackTrace(); }
    }

    public void move()
    {
        y = y+road.player.speedPlayer;
    }
}
