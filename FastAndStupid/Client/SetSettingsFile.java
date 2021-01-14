package FastAndStupid.Client;

import java.io.*;

public class SetSettingsFile { // get parameters in .conf file and take it into Settings class
    public SetSettingsFile()
    {
        try
        {
            FileReader fReader = new FileReader(getClass().getResource("/SettingsConfiguration.conf").getFile());
            BufferedReader bReader = new BufferedReader(fReader);
            String line = bReader.readLine();
            while (line != null) {
                switch (line.substring(0,line.indexOf('=')))
                {
                    case "windowWidth":
                        Settings.windowWidth = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "windowHeight":
                        Settings.windowHeight = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "moveCar":
                        Settings.moveCar = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "roadLeftBottom":
                        Settings.roadLeftBottom = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "roadRightBottom":
                        Settings.roadRightBottom = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "topSpeedCar":
                        Settings.topSpeedCar = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "minSpeedCar":
                        Settings.minSpeedCar = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "defaultCarPositionX":
                        Settings.defaultCarPositionX = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "defaultCarPositionY":
                        Settings.defaultCarPositionY = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "timerDelay":
                        Settings.timerDelay = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "maxPoliceCarSpeed":
                        Settings.maxPoliceCarSpeed = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "maxDelaySpawnPoliceCar":
                        Settings.maxDelaySpawnPoliceCar = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "policeSoundsDelay":
                        Settings.policeSoundsDelay = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "lightsOnPlayerCar":
                        Settings.lightsOnPlayerCar = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "livesPlayerCar":
                        Settings.livesPlayerCar = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                    case "godMode":
                        Settings.godMode = Integer.parseInt(line.substring(line.indexOf('=')+1));
                        break;
                }
                line = bReader.readLine();
            }
            bReader.close();
            fReader.close();
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}
