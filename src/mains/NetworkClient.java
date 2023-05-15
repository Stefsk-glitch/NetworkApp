package mains;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NetworkClient
{
    public static void main(String[] args)
    {
        boolean isLoginIn = false;

        try
        {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            int counter = 1;
            String name = "";
            String text = "";

            while (true)
            {
                // lees de text van de server
                String serverText = in.readLine();

                if(!(serverText == null))
                {
                    System.out.println(serverText);
                }

                // stuur een bericht naar de server
                Scanner scanner = new Scanner(System.in);

                if (counter == 1)
                {
                    text = scanner.nextLine();
                    name = text;
                    out.println(text);
                }

                if (counter == 2)
                {
                    text = scanner.nextLine();
                    out.println(text);
                }

                if(!(serverText == null))
                {
                    if (serverText.equals("Welkom " + name + "!"))
                    {
                        isLoginIn = true;
                    }

                    if (serverText.equals("Ongeldige gebruikersnaam of wachtwoord"))
                    {
                        break;
                    }
                }

                if (isLoginIn)
                {
                    if(!(serverText == null))
                    {
                        if (serverText.equals("Dit zijn alle acties die u kan uitvoeren op de logs: 'add', 'remove' of 'show': "))
                        {
                            text = scanner.nextLine();
                            out.println(text);
                        }

                        if (serverText.equals("Type de tekst die u wilt toevoegen aan uw logs: "))
                        {
                            text = scanner.nextLine();
                            out.println(text);
                        }

                        if (serverText.equals("Type de tekst die u wilt verwijderen van uw logs: "))
                        {
                            text = scanner.nextLine();
                            out.println(text);
                        }

                        if (serverText.equals("finished - connectie wordt kapot gemaakt!"))
                        {
                            break;
                        }
                    }
                }

                counter++;
            }

            // maak de verbinding kapot
            socket.close();
        }

        catch (IOException e)
        {
            System.out.println("Server is not online!");
            //e.printStackTrace();
        }
    }
}