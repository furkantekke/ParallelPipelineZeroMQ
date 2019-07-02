/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deneme2;

/**
 *
 * @author furkantekke
 */
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class taskwork
{
    public static void main(String[] args) throws Exception
    {
        try (ZContext context = new ZContext()) {
            //  Socket to receive messages on
            ZMQ.Socket receiver = context.createSocket(SocketType.PULL);
            receiver.connect("tcp://localhost:5557");

            ZMQ.Socket tabanAlici = context.createSocket(SocketType.PULL);
            tabanAlici.connect("tcp://localhost:5559");
          
            
            //  Socket to send messages to
            ZMQ.Socket sender = context.createSocket(SocketType.PUSH);
            sender.connect("tcp://localhost:5558");

            //  Process tasks forever
            while (!Thread.currentThread().isInterrupted()) {
                int taban = Integer.parseInt(tabanAlici.recvStr());
                String string = new String(receiver.recv(0), ZMQ.CHARSET).trim();
                long power = Long.parseLong(string);
                //  Simple progress indicator for the viewer
                System.out.flush();
                System.out.print(string + '.');
                long sonuc = usAlma(taban,power);
                System.out.println("sonuç: "+sonuc);
                
                
                Thread.sleep(power);

                //  Send results to sink
                sender.send(sonuc+"",0);
            }
        }
        
    }
    private static long usAlma(int taban,long power){
        long sayi = 1;
        for(long i=0; i<power; i++){
            sayi*=taban;
        }
        return sayi;
    }
}