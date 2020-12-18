package FastAndStupid;

import FastAndStupid.Server.ConnectToDB;
import FastAndStupid.Server.Server;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class StartServer extends JFrame {

    private Server server;
    private List<String> usedPorts = new ArrayList<>();

    public StartServer() throws ParseException {    // generate server form. Same as StartClient but simplified
        super("Создание сервера");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel textPort = new JLabel("Порт сервера (рекомендовано 5000):");
        JLabel textStatus = new JLabel("Статус сервера: ");
        JLabel statusValue = new JLabel("offline");
        JButton buttonOK = new JButton("Запустить сервер");
        JLabel textNotice= new JLabel("Сервер не может быть запущен, измените порт!");

        statusValue.setForeground(Color.red.darker());

        textNotice.setForeground(Color.red.darker());
        textNotice.setVisible(false);

        MaskFormatter portFormatter = new MaskFormatter("####");
        portFormatter.setPlaceholderCharacter('0');
        JFormattedTextField portField = new JFormattedTextField(portFormatter);
        portField.setColumns(4);
        portField.setText("5000");

        JPanel contents = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contents.add(textPort);
        contents.add(portField);
        contents.add(buttonOK);
        setContentPane(contents);
        contents.add(textStatus);
        contents.add(statusValue);
        contents.add(textNotice);
        setContentPane(contents);

        buttonOK.addActionListener(e -> {
            if (buttonOK.getText().equals("Запустить сервер"))
            {
                if (usedPorts.contains(portField.getText())) textNotice.setVisible(true);
                else {
                    usedPorts.add(portField.getText());
                    server = new Server(Integer.parseInt(portField.getText()));
                    buttonOK.setText("Остановить сервер");
                    statusValue.setText("online");
                    statusValue.setForeground(Color.green.darker());
                    textNotice.setVisible(false);
                }
            }
            else {
                server.stopServer();
                buttonOK.setText("Запустить сервер");
                int newPort = Integer.parseInt(portField.getText())+1;
                portField.setText(String.valueOf(newPort));
                statusValue.setText("offline");
                statusValue.setForeground(Color.red.darker());
            }
        });

        setSize(330, 120);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws ParseException {
        new StartServer();
    }
}
