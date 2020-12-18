package FastAndStupid.Client;

import javax.sound.sampled.*;
import java.net.URL;

public class PlayMusic {
    private Clip clip;                  // music storage
    private boolean onePlay = false;    // one play or endless loop
    private int wait;                   // time before playing
    public PlayMusic(URL soundFile, boolean onePlay, int volume, int waitMillSecBeforePlaying) throws Exception {
        this.onePlay = onePlay;
        this.wait = waitMillSecBeforePlaying;
        AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);
        AudioFormat format = stream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(), ((int) stream.getFrameLength() * format.getFrameSize()));
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(stream);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); // set volume
        gainControl.setValue(volume);
    }
    public void play(){
        new PlayerMusic();
    }

    public boolean active(){ return clip.isActive(); }

    public void stop(){ clip.stop(); }

    private class PlayerMusic extends Thread {      // play music in another thread
        public PlayerMusic() {
            this.start();
        }

        public void run() {
            try { Thread.sleep(wait); } catch (InterruptedException ignored) { }
            if (onePlay)clip.start();
            else clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
}