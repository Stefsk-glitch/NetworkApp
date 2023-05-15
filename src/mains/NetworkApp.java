package mains;

import handlers.AcceptHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tree.*;

import java.io.FileWriter;
import java.net.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkApp
{
    public static void main(String[] args)
    {
        // maak een binary search tree aan om gebruikersnamen en wachtwoorden op te slaan
        BinarySearchTree users = new BinarySearchTree();
        users.put("Astrid", "password1");
        users.put("Bob", "password2");
        users.put("Pieter", "password3");
        users.put("Kip", "password4");
        users.put("David", "passwor");
        users.put("Mick", "daf");
        users.put("Max", "423425");
        users.put("Joost", "naam");
        users.put("Bas", "jaja");
        users.put("Stef", "oki");
        users.put("Lucas", "daar");
        users.put("Nick", "hunnn");

        JSONObject json = new JSONObject();
        JSONArray peopleArray = new JSONArray();

        json.put("Person", peopleArray);

        List<String> nameList = new ArrayList<>(users.getAllKeys());

        for (String name : nameList)
        {
            JSONObject person = new JSONObject();

            ArrayList logs = new ArrayList<>();

            person.put("id", name);
            person.put("logs", logs);

            peopleArray.add(person);
        }

        try (FileWriter file = new FileWriter("logs.json")) {
            file.write(json.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // start de server op
        ServerSocket serverSocket = null;

        try
        {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server is gestart op poort 5000");
        }

        catch (IOException e)
        {
            System.out.println("Kon geen verbinding maken op poort 5000");
            System.exit(-1);
        }

        // start de thread om inkomende verbindingen te verwerken
        Thread acceptThread = new Thread(new AcceptHandler(serverSocket, users));
        acceptThread.start();

        // blijf de main thread actief houden zodat de applicatie niet afsluit
        while (true)
        {

        }
    }
}