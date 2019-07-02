
package deneme2;

import java.util.Scanner;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;


public class Deneme2
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

            System.out.println("İşçiler hazır olduğunda enter a basınız: ");
            System.in.read();
            System.out.println("Görevler işçilere gönderilecek\n");

                System.out.println("Üssü alınacak sayıyı giriniz");
            Scanner scan = new Scanner(System.in);
            Integer taban= scan.nextInt();
            
            
            System.out.println("Üs giriniz");
            Integer us= scan.nextInt();
            
             System.out.println("Kaç işçi ile hesaplamak istersiniz");
             Integer isci= scan.nextInt();
            
             Integer dizi[]=new Integer[isci];
             
             for(int i=0;i<isci;i++){
              System.out.println((i+1)+". iscinin gücünü giriniz");
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