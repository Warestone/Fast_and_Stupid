package FastAndStupid.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.List;

public class Road extends JPanel implements ActionListener, Runnable {  //main class of the program
    Timer timer = new Timer(Settings.timerDelay, this);             // timer for update game state
    Image imgRoad = new ImageIcon(getClass().getResource("/img/road/road.png")).getImage();     //road
    Image imgLight = new ImageIcon(getClass().getResource("/img/road/lights.png")).getImage();
    int livesPlayerCar = Settings.livesPlayerCar;                           // lives of player
    Thread policeFactory = new Thread(this);                           // generator police cars
    List<PoliceCar> parkingPolice = new ArrayList<PoliceCar>();                      // generated list of police cars for spawning
    PlayerCar player = new PlayerCar();                                      // instance of player
    File[] policeSounds;                                                     // random police sound in game
    boolean playPoliceSound = true;                                          // for stop playing sounds in thread
    PlayMusic music;                                                         // in game music

    public Road () {
        timer.start();
        addKeyListener(new FastStupidKeyAdapter());                          // key listener
        setFocusable(true);
        policeFactory.start();                                               // start police cars generator

        // play start police sound
        try {new PlayMusic(getClass().getResource("/sounds/PoliceReportSounds/policeStart.wav"),true,-5,0).play();} catch (Exception ignored){}
        File[] musicFiles = getMusicFiles("/sounds/music");

        // play random music in folder (only wav 25b may add)
        if (musicFiles!=null)  try {music = new PlayMusic(musicFiles[(int) (Math.random() * musicFiles.length)].toURI().toURL(),false, -15,5300); music.play(); } catch (Exception ignored) {}
        policeSounds = getMusicFiles("/sounds/policeSounds");
        if (policeSounds!=null) new PoliceSoundsPlayer(); // play random police sounds in game
    }

    private class FastStupidKeyAdapter extends KeyAdapter                   // send key pressed event to PlayerCar class
    {
        public void keyPressed(KeyEvent event) { player.keyPressed(event); }
        public void keyReleased(KeyEvent event) { player.keyReleased(event); }
    }
    public void paint (Graphics graphics)           // paint objects on JFrame window
    {
        graphics.drawImage(imgRoad,0,player.layer1, null);  // road layer 1
        graphics.drawImage(imgRoad,0,player.layer2, null);  // road layer 2
        Iterator<PoliceCar> iterator = parkingPolice.iterator();
        while (iterator.hasNext())
        {
            PoliceCar policeCar = iterator.next();
            if (policeCar.y>Settings.windowHeight){     // if police car is behind of player car - remove (otherwise memory leak)
                iterator.remove();
            }
            else
            {
                policeCar.move();
                graphics.drawImage(policeCar.imgPolice,policeCar.x,policeCar.y,null);
            }
        }
        graphics.drawImage(player.imgPlayer,player.x,player.y,null);
        graphics.drawImage(imgLight,0,player.layer1, null);
        graphics.drawImage(imgLight,0,player.layer2, null);
    }

    private File[] getMusicFiles(String path)       // method for search files in folder 'path'
    {
        File[] musicFiles;
        try {
            File musicDir = new File(getClass().getResource(path).getFile());
            musicFiles = musicDir.listFiles();
        }
        catch (Exception e) {System.out.println("\n\nUnable to play music. Error: "+e);return null;}
        return musicFiles;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) { // call every tick timer (update all states)
        player.move();
        repaint();
        crashTest();
    }

    private void crashTest() {                                      // check collision player car and police car
        Iterator<PoliceCar> iterator = parkingPolice.iterator();
        int index = 0;
        while (iterator.hasNext())
        {
            PoliceCar policeCar = iterator.next();
            if (player.getRectangle().intersects(policeCar.getRectangle()) && !policeCar.nameImage.toString().startsWith("break"))
            {
                if (Settings.godMode==0)livesPlayerCar--;
                if (livesPlayerCar==2 || livesPlayerCar == 1 && Settings.godMode==0) // update models of player car when crash
                {
                    try
                    {
                        player.imgPlayerStandardCar = new ImageIcon(getClass().getResource("/img/playerCar/car"+livesPlayerCar+".png")).getImage();
                        player.imgPlayerLeftCar = new ImageIcon(getClass().getResource("/img/playerCar/carLeft"+livesPlayerCar+".png")).getImage();
                        player.imgPlayerRightCar = new ImageIcon(getClass().getResource("/img/playerCar/carRight"+livesPlayerCar+".png")).getImage();
                        player.imgPlayerSpeedUpCar = new ImageIcon(getClass().getResource("/img/playerCar/carFast"+livesPlayerCar+".png")).getImage();
                        player.imgPlayerSpeedDownCar = new ImageIcon(getClass().getResource("/img/playerCar/carStop"+livesPlayerCar+".png")).getImage();
                        player.imgPlayer=player.imgPlayerStandardCar;
                    }
                    catch (Exception ignored){}
                }
                // update police car model when crash
                try { policeCar.imgPolice = new ImageIcon(getClass().getResource("/img/breakPoliceCar/break"+policeCar.nameImage)).getImage();}catch (Exception ignored){}
                policeCar.nameImage.insert(0,"break");
                parkingPolice.set(index,policeCar);

                // play crash sound
                try{new PlayMusic(getClass().getResource("/sounds/PoliceReportSounds/policeCrash.wav"),true,-3,0).play();} catch (Exception ignored){}
                if (livesPlayerCar==0) // when player haven't any lives
                {
                    // play policeStop sound, view a message & stop game
                    playPoliceSound = false;
                    music.stop();
                    try{ new PlayMusic(getClass().getResource("/sounds/PoliceReportSounds/policeBusted.wav"),true,-5,0).play();} catch (Exception ignored){}
                    JOptionPane.showMessageDialog(null,"Вас задержали.\nБлижайшие несколько лет у Вас будет номер с видом на море в тюрьме 'Алькатрас'.");
                    // INSERT ADD SCORE IN DB
                    System.exit(0);
                }
                index++;
            }
        }
    }

    @Override
    public void run() {
        while (true)
        {
            Random random = new Random();
            try {
                // police car factory
                Thread.sleep(1000+random.nextInt(Settings.maxDelaySpawnPoliceCar));
                int positionPolice = random.nextInt(Settings.windowWidth);
                if (positionPolice<Settings.roadLeftBottom)positionPolice += Settings.roadLeftBottom-positionPolice;
                else if (positionPolice>Settings.roadRightBottom)positionPolice -= positionPolice-Settings.roadRightBottom;
                parkingPolice.add(new PoliceCar(positionPolice,-Settings.windowHeight,Settings.maxPoliceCarSpeed,this));
            } catch (Exception ignored) {}
        }
    }

    private class PoliceSoundsPlayer extends Thread // police sounds endless factory
    {
        public PoliceSoundsPlayer()
        { this.start(); }

        public void run() {
            while (playPoliceSound)
            {
                try { Thread.sleep(Settings.policeSoundsDelay); } catch (InterruptedException ignored) { }
                try { if (playPoliceSound) new PlayMusic(policeSounds[(int) (Math.random() * policeSounds.length)].toURI().toURL(),true, -5,0).play(); } catch (Exception ignored) { }
            }
        }
    }
}
