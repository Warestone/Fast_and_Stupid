package FastAndStupid.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.InputMismatchException;

public class ConnectToServer {
    String message;                         // sending message to server
    int serverPort;                         // port of server

    public ConnectToServer(String message, int serverPort)
    {
        setMessage(message);
        this.message = message;
        this.serverPort = serverPort;
    }

    public void setMessage(String message) {
        if (message == null) throw new InputMismatchException("The message to server don't be null");
        if (message.equals("")) throw new InputMismatchException("The message to server don't be empty");
        this.message = message;
    }

    public String sendMessage(){
        try (Socket socket = new Socket("127.0.0.1", serverPort))               // server on localhost
        {
            socket.setSoTimeout(10000);                                              // max waiting server
            try (PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true))    // try to connect
            {
                toServer.println(message);                                       //send message
                BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputFromServer = fromServer.readLine();                  //read answer from server
                fromServer.close();
                return inputFromServer;
            }
        }
        catch (SocketException | UnknownHostException socketException) { socketException.printStackTrace(); }
        catch (IOException e) {
            try {
                if (e instanceof SocketTimeoutException) { throw new SocketTimeoutException(); }
                else e.printStackTrace();
            }
            catch (SocketTimeoutException ste) { System.out.println("Turn off the client by timeout"); }
        }
        return "error";
    }
}
