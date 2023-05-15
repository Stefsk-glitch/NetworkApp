import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.*;
import org.json.*;

import java.io.*;
import java.net.Socket;
import java.util.stream.Collectors;

class ClientHandler implements Runnable
{
    private Socket clientSocket;
    private BinarySearchTree users;

    public ClientHandler(Socket clientSocket, BinarySearchTree users)
    {
        this.clientSocket = clientSocket;
        this.users = users;
    }

    public void run()
    {
        try
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // vraag om gebruikersnaam en wachtwoord
            output.println("Voer uw gebruikersnaam in:");
            String username = input.readLine();
            output.println("Voer uw wachtwoord in:");
            String password = input.readLine();

            // controleer of de gebruikersnaam en wachtwoord geldig zijn
            if (users.containsKey(username) && users.get(username).equals(password))
            {
                output.println("Welkom " + username + "!");
                output.println("Dit zijn alle acties die u kan uitvoeren op de logs: 'add', 'remove' of 'show': ");
                String action = input.readLine();
                output.println("De actie die u heeft gekozen: " + action);

                if (action.equals("add"))
                {
                    output.println("Type de tekst die u wilt toevoegen aan uw logs: ");
                    String text = input.readLine();

                    BufferedReader reader = new BufferedReader(new FileReader("logs.json"));
                    String jsonString = reader.lines().collect(Collectors.joining());

                    JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);
                    JSONArray personArray = (JSONArray) jsonObject.get("Person");

                    for (Object personObject : personArray) {
                        JSONObject person = (JSONObject) personObject;
                        if (person.get("id").equals(username)) {
                            JSONArray logsArray = (JSONArray) person.get("logs");
                            logsArray.add(text);
                            break;
                        }
                    }

                    String updatedJsonString = jsonObject.toJSONString();

                    try
                    {
                        FileWriter fileWriter = new FileWriter("logs.json");
                        fileWriter.write(updatedJsonString);
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    output.println("finished - connectie wordt kapot gemaakt!");
                }

                if (action.equals("remove"))
                {
                    output.println("Type de tekst die u wilt verwijderen van uw logs: ");
                    String text = input.readLine();

                    BufferedReader reader = new BufferedReader(new FileReader("logs.json"));
                    String jsonString = reader.lines().collect(Collectors.joining());

                    JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);
                    JSONArray personArray = (JSONArray) jsonObject.get("Person");

                    for (Object personObject : personArray)
                    {
                        JSONObject person = (JSONObject) personObject;
                        if (person.get("id").equals(username))
                        {
                            JSONArray logsArray = (JSONArray) person.get("logs");

                            for (int i = 0; i < logsArray.size(); i++)
                            {
                                if (logsArray.get(i).toString().equals(text))
                                {
                                    logsArray.remove(i);
                                }
                            }

                            break;
                        }
                    }

                    String updatedJsonString = jsonObject.toJSONString();

                    try
                    {
                        FileWriter fileWriter = new FileWriter("logs.json");
                        fileWriter.write(updatedJsonString);
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    output.println("finished - connectie wordt kapot gemaakt!");
                }

                if (action.equals("show"))
                {
                    BufferedReader reader = new BufferedReader(new FileReader("logs.json"));
                    String jsonString = reader.lines().collect(Collectors.joining());

                    JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);
                    JSONArray personArray = (JSONArray) jsonObject.get("Person");

                    for (Object personObject : personArray) {
                        JSONObject person = (JSONObject) personObject;
                        if (person.get("id").equals(username)) {
                            JSONArray logsArray = (JSONArray) person.get("logs");

                            output.println("De logs: ");

                            for (Object o : logsArray)
                            {
                                output.println(" - " + o);
                            }

                            break;
                        }
                    }

                    output.println("finished - connectie wordt kapot gemaakt!");
                }
            }
            else
            {
                output.println("Ongeldige gebruikersnaam of wachtwoord");
            }

            // sluit de verbinding
            clientSocket.close();
        }

        catch (IOException e)
        {
            System.out.println("Er is een fout opgetreden bij de verwerking van de verbinding");
        }
    }
}