package handlers;

import tree.*;

import java.net.*;
import java.io.IOException;

public class AcceptHandler implements Runnable
{
    private ServerSocket serverSocket;
    private BinarySearchTree users;

    public AcceptHandler(ServerSocket serverSocket, BinarySearchTree users)
    {
        this.serverSocket = serverSocket;
        this.users = users;
    }

    @Override
    public void run()
    {
        // wacht op inkomende verbindingen
        while (true)
        {
            Socket clientSocket = null;

            try
            {
                clientSocket = serverSocket.accept();
                System.out.println("Nieuwe verbinding van " + clientSocket.getInetAddress().getHostAddress());

                // maak een nieuwe thread om de verbinding te verwerken
                ClientHandler clientHandler = new ClientHandler(clientSocket, users);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }

            catch (IOException e)
            {
                System.out.println("Kon geen verbinding accepteren");
            }
        }
    }
}
