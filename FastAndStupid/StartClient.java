package FastAndStupid;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import FastAndStupid.Client.ConnectToServer;
import FastAndStupid.Client.Road;
import FastAndStupid.Client.SetSettingsFile;
import FastAndStupid.Client.Settings;
import FastAndStupid.Server.ConnectToDB;

import java.awt.*;
import java.text.ParseException;

public class StartClient {
    public static void main(String [] Args) {
        try {showJFrameDialog(); }
        catch (ParseException e) { e.printStackTrace(); }
    }

    private static void showJFrameDialog() throws ParseException {
        JFrame frame = new JFrame("Fast & Stupid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create objects in form
        JLabel textPort = new JLabel("Порт сервера (рекомендовано 5000):");
        JLabel textLogin = new JLabel("Логин:");
        JLabel textPassword = new JLabel("\nПароль:");
        JLabel textMessage = new JLabel("                ");
        JButton buttonLogin = new JButton("Авторизоваться");
        JButton buttonNewUser = new JButton("Добавить");
        JButton buttonStartWithoutServer = new JButton("Продолжить без аккаунта");

        MaskFormatter formatter = new MaskFormatter("####");  // format '0000' for port field
        formatter.setPlaceholderCharacter('0');
        JFormattedTextField portField = new JFormattedTextField(formatter);
        portField.setColumns(4);
        portField.setText("5000");

        TextField loginField = new TextField();
        loginField.setColumns(10);
        loginField.setText("Player");

        TextField passwordField = new TextField();
        passwordField.setColumns(10);
        passwordField.setText("password");

        textMessage.setForeground(Color.red.darker());
        textMessage.setVisible(false);

        JPanel contents = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contents.add(textPort);
        contents.add(portField);
        contents.add(textLogin);
        contents.add(loginField);
        contents.add(textPassword);
        contents.add(passwordField);
        contents.add(buttonLogin);
        contents.add(buttonNewUser);
        contents.add(textMessage);
        contents.add(buttonStartWithoutServer);
        frame.setContentPane(contents);                             // add objects to form

        frame.setSize(330, 175);                         // set form parameters
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        buttonLogin.addActionListener(e -> {                        // when authentication button is pressed
            // send message to server and receive answer
            ConnectToServer connectToServer = new ConnectToServer("auth:"+loginField.getText()+":"+passwordField.getText(),Integer.parseInt(portField.getText()));
            String messageServer = connectToServer.sendMessage();
            if (messageServer.contains("done"))
            {
                JOptionPane.showMessageDialog(null,"Добро пожаловать, "+loginField.getText()+"!\nИгра начнётся после закрытия этого сообщения.");
                frame.setVisible(false);
                startGame();
            }
            if (messageServer.contains("error"))
            {
                textMessage.setText("Сервер с портом "+portField.getText()+" не найден.");
                textMessage.setVisible(true);
            }
            if (messageServer.contains("loginInc"))
            {
                textMessage.setText("Неверный логин!");
                textMessage.setVisible(true);
            }
            if (messageServer.contains("passwordInc"))
            {
                textMessage.setText("Неверный пароль!");
                textMessage.setVisible(true);
            }
        });

        buttonNewUser.addActionListener(e -> { // when add new user button is pressed
            ConnectToServer connectToServer = new ConnectToServer("new:"+loginField.getText()+":"+passwordField.getText(),Integer.parseInt(portField.getText()));
            String messageServer = connectToServer.sendMessage();
            if (messageServer.equals("done"))
            {
                JOptionPane.showMessageDialog(null,"Игрок '"+loginField.getText()+"' успешно добавлен!\nИгра начнётся после закрытия этого сообщения.");
                frame.setVisible(false);
                startGame();
            }
            if (messageServer.equals("error"))
            {
                textMessage.setText("Сервер с портом "+portField.getText()+" не найден.");
                textMessage.setVisible(true);
            }
            if (messageServer.equals("addError"))
            {
                textMessage.setText("Произошла ошибка при добавлении нового игрока.\nПопробуйте ввести новый логин и пароль!");
                textMessage.setVisible(true);
                loginField.setText("player");
                passwordField.setText("password");
            }
        });

        buttonStartWithoutServer.addActionListener(e -> { // when start offline button is pressed
            JOptionPane.showMessageDialog(null,"Игра начнётся после закрытия этого сообщения.");
            frame.setVisible(false);
            startGame();
        });
    }

    private static void startGame() {
        new SetSettingsFile();          // upload setting from .conf file (or default, when IOException)
        JFrame frame = new JFrame("Fast & Stupid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Settings.windowWidth, Settings.windowHeight);
        frame.setResizable(false);
        frame.add(new Road());              //start game
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
