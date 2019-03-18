import java.net.Socket;
import java.net.InetAddress;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.net.*;

public class Stress1
{
  public static void main(String[] args)
  {
    int nbCS=0;
    SocketChannel sc;
    ByteBuffer buffer;
    int port=2222;

    try
    {
      if(args.length != 2 || Integer.parseInt(args[0])<=0)
      {
        System.out.println("Passez en argument le port et le nombre de clients stressants (>0).");
        return;
      }
      else
      {
        nbCS = Integer.parseInt(args[1]);
        port = Integer.parseInt(args[0]);
        System.out.println("Il y a "+nbCS+" clients stressants");
      }

      for(int i=1; i<nbCS+1; i++)
      {
        double temps = System.nanoTime();

        sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", port));

        buffer = ByteBuffer.allocate(40);
        buffer.clear();
        String text = "client stress1 n°"+i;
        buffer.put(text.getBytes());
        buffer.flip();

        while(buffer.hasRemaining())
        {
          sc.write(buffer);
        }
      }
    }catch(Exception e)
    {
      System.out.println("Une erreur s'est produite");
      e.printStackTrace();
    }
  }
}
