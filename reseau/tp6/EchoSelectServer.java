/* echo / serveur basique
   Master Informatique 2012 -- Université Aix-Marseille
   Bilel Derbel, Emmanuel Godard
*/

// https://gist.github.com/komamitsu/1528682

import java.net.*;
import java.nio.channels.*;
import java.io.*;
import java.util.Set;
import java.util.Iterator;

class EchoSelectServer {

  /* Démarrage et délégation des connexions entrantes */
  public void demarrer(int port) {
    ServerSocketChannel serv_socket; // socket d'écoute utilisée par le serveur
    SocketChannel cli_socket;

    System.out.println("Lancement du serveur sur le port " + port);
    try
    {
      serv_socket = ServerSocketChannel.open();
      serv_socket.socket().bind(new InetSocketAddress(port));
      serv_socket.socket().setReuseAddress(true); /* rend le port réutilisable rapidement */
      serv_socket.configureBlocking(false);

      Selector selector = Selector.open();
      serv_socket.register(selector, SelectionKey.OP_ACCEPT);

      while (true)
      {
         int channelCount = selector.selectedKeys().size();
         if(channelCount > 0)
         {
           Set<SelectionKey> keys = selector.selectedKeys();
           Iterator<SelectionKey> iterator = keys.iterator();
           while (iterator.hasNext())
           {
             SelectionKey key = iterator.next();
             iterator.remove();

             if(key.isAcceptable())
             {

             }
             if(key.isReadable())
             {

             }
             if(key.isWritable())
             {

             }

             cli_socket = serv_socket.accept();
             cli_socket.configureBlocking(false);
             cli_socket.register(selector, SelectionKey.OP_READ, cli_socket.socket().getPort());
             Handler ch = new Handler(cli_socket);
          }
         }
      }
    } catch (IOException ex)
    {
      System.out.println("Arrêt anormal du serveur."+ ex);
      return;
    }
  }

  public static void main(String[] args) {
    int argc = args.length;
    EchoServer serveur;

    /* Traitement des arguments */
    if (argc == 1)
    {
      try
      {
        serveur = new EchoServer();
        serveur.demarrer(Integer.parseInt(args[0]));
      } catch (Exception e)
      {
        e.printStackTrace();
      }
    } else
    {
      System.out.println("Usage: java EchoServer port");
    }
    return;
  }

  /*
     echo des messages reçus (le tout via la socket).
     NB classe Runnable : le code exécuté est défini dans la
     méthode run().
  */
  class Handler implements Runnable {

    SocketChannel socket;
    PrintWriter out;
    BufferedReader in;
    InetAddress hote;
    int port;

    Handler(SocketChannel socket) throws IOException
    {
      this.socket = socket;
      out = new PrintWriter(socket.socket().getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.socket().getInputStream()));
      hote = socket.socket().getInetAddress();
      port = socket.socket().getPort();
    }

    public void run()
    {
      String tampon;
      long compteur = 0;

      try
      {
        /* envoi du message d'accueil */
        out.println("Bonjour " + hote + "! (vous utilisez le port " + port + ")");

        do
        {
          /* Faire echo et logguer */
          tampon = in.readLine();
          if (tampon != null)
          {
            compteur++;
            /* log */
            System.err.println("[" + hote + ":" + port + "]: " + compteur + ":" + tampon);
            /* echo vers le client */
            out.println("> " + tampon);
          } else
          {
            break;
          }
        } while (true);

        /* le correspondant a quitté */
        if(!socket.socket().isClosed())
        {
          in.close();
          out.println("Au revoir...");
          out.close();
          socket.close();

          System.err.println("[" + hote + ":" + port + "]: Terminé...");
        }
      } catch (Exception e)

      {
        e.printStackTrace();
      }
    }
  }
}
