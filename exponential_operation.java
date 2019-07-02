
package exponential_operation;

import java.util.Scanner;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;


public class Exponential_operation
{
    public static void main(String[] args) throws Exception
    {
        try (ZContext context = new ZContext()) {
            
            ZMQ.Socket sender = context.createSocket(SocketType.PUSH);
            sender.bind("tcp://*:5557");

            ZMQ.Socket tabanGonderici = context.createSocket(SocketType.PUSH);
            tabanGonderici.bind("tcp://*:5559");
          
          
            ZMQ.Socket sink = context.createSocket(SocketType.PUSH);
            sink.connect("tcp://localhost:5558");

            System.out.println("Press enter when workers are ready: ");
            System.in.read();
            System.out.println("Tasks will send to workers\n");

                System.out.println("Base number for exponential operation");
            Scanner scan = new Scanner(System.in);
            Integer taban= scan.nextInt();
            
            
            System.out.println("Exponent number for exponential operation");
            Integer us= scan.nextInt();
            
             System.out.println("How many worker do you want");
             Integer isci= scan.nextInt();
            
             Integer dizi[]=new Integer[isci];
             
             for(int i=0;i<isci;i++){
              System.out.println((i+1)+". worker power");
            Integer guc= scan.nextInt();
            dizi[i]=guc;
             }
            
            sink.send("0", 0);

            int task_nbr;
            int total_msec = 0; 
             Long sonuc[]=new Long[isci];
            for (task_nbr = 0; task_nbr < isci; task_nbr++) {
                taskwork worker = new taskwork();
                String gidenUs = dizi[task_nbr]+"";
                sender.send(gidenUs, 0);
                tabanGonderici.send(taban+"",0);

            }
            
            Thread.sleep(1000); //  Give 0MQ time to deliver
        }
    }
}
