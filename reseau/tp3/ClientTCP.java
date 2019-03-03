import java.util.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.net.Socket;
import java.net.*;
import java.io.*;


public class ClientTCP
{
  public static void main(String[] args)
  {
    InetAddress adr;
    int port;

    try
    {
      if (args.length==2)
      {
        adr = InetAddress.getByName(args[0]);
        port = Integer.parseInt(args[1]);
      }
      else
      {
        System.out.println("Adresse et/ou port manquant");
        System.out.println("Veuillez passer l'adresse en premier agrument et le port en second.");
        return;
      }

      Socket socket;
      DataInputStream userInput;
      PrintStream theOutputStream;

        InetAddress serveur = InetAddress.getByName("localhost");
        socket = new Socket(serveur, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream out = new PrintStream(socket.getOutputStream());
        out.println(args[0]);
        System.out.println(in.readLine());

      socket.close();
    }
    catch(Exception e)
    {
      System.out.println("Une erreur s'est produite");
      e.printStackTrace();
    }
  }
}
