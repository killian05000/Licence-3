import java.util.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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

      Socket socket = new Socket(adr, port);
      BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
      PrintWriter out = new PrintWriter(socket.getOutputStream());
      System.out.println("Client connected");
      String msg;

      do
      {
        msg = userIn.readLine();
        out.println(msg);
        out.flush();
      } while (msg != "stop");

      socket.close();
    }
    catch(Exception e)
    {
      System.out.println("Une erreur s'est produite");
      e.printStackTrace();
    }
  }
}
