package FastAndStupid.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {            // Server class listen the port, get messages, cal database in resend database message to client
    private boolean serverOnline;
    private ServerSocket serverSocket;
    private int port;

    public Server(int port) {
        this.port = port;
        startServer();          // start server when initialize
    }

    private void startServer() {
        serverOnline = true;
        this.start();
    }

    public void stopServer() {
        serverOnline = false;
        System.out.println("Server stopped.");
    }

    public void run() {
        try
        {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started.");
            while (serverOnline) {
                Socket server = serverSocket.accept(); // run server

                // listen and read messages from Client
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(server.getInputStream()));
                String line = fromClient.readLine();
                System.out.println(line);
                String[] data = line.split(":");
                String status = "error";

                // call connect to db and send sql query
                if (line.contains("new:")) status = new ConnectToDB().executeQuery("INSERT INTO scores (name, password, total) VALUES('"+data[1]+"','"+data[2]+"',0);");
                if (line.contains("auth:")) status = "done";
                if (line.contains("upd:")) status = "done";

                // send message to client
                PrintWriter toClient = new PrintWriter(server.getOutputStream(), true);
                toClient.println(status);
            }
            serverSocket.close();
        }
        catch (Exception ex) {ex.printStackTrace();}
    }
}
