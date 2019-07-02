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
import java.util.ArrayList;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

//
//  Task sink in Java
//  Binds PULL socket to tcp://localhost:5558
//  Collects results from workers via that socket
//
public class tasksink
{
    public static void main(String[] args) throws Exception
    {
        ArrayList<Long> sonuclar = new ArrayList<Long>();
        long sonuc1 = 1;
        //  Prepare our context and socket
        long tstart = System.currentTimeMillis();
        try (ZContext context = new ZContext()) {
            ZMQ.Socket receiver = context.createSocket(SocketType.PULL);
            receiver.bind("tcp://*:5558");

            //  Wait for start of batch
            String string = new String(receiver.recv(0), ZMQ.CHARSET);
            int task_nbr;
            int total_msec = 0; //  Total calculated cost in msecs
            for (task_nbr = 0; task_nbr < 100; task_nbr++) {
                string = new String(receiver.recv(0), ZMQ.CHARSET).trim();
                sonuc1 = sonuc1 * Long.parseLong(string);
                System.out.println(sonuc1);
                long end = System.currentTimeMillis();

            System.out.println("\tToplam geçen zaman: " + (end - tstart) + " msec\n");
           
            }
            
       
        }
    }
}
