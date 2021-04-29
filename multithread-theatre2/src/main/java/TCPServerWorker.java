import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class TCPServerWorker extends Thread{
    private final Socket establishedSocket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private final Object lock1 = new Object();

    volatile static LinkedList<Reservations> seats = new LinkedList<>();
    volatile static int seatNum = 1;
    //AtomicInteger atomicInteger = new AtomicInteger(1);


    public TCPServerWorker(Socket s){
        establishedSocket = s;
        try {
            inFromClient = new BufferedReader(new InputStreamReader(establishedSocket.getInputStream()));
            outToClient = new DataOutputStream(establishedSocket.getOutputStream());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void run() {
        String stringTickets;
        //data = ticketNumber;
        int tickets, i, flag = 1;
        int data = -1;

        try{
            stringTickets = inFromClient.readLine();
            tickets = Integer.parseInt(stringTickets);
            //synchronize + atomicity
            synchronized (lock1){
                //if seatNum + tickets > maxSeats ANNULLA
                Reservations booker = new Reservations(seatNum + tickets);
                System.out.println(seatNum);
                if(booker.checkSeats() >= -1){
                    for(i = 0; i < tickets; i++){
                        booker = new Reservations(seatNum);
                        seats.add(booker);
                        data = booker.checkSeats();
                        //System.out.println(data);
                        seatNum++;
                        flag = 0;
                    }
                }
                else{
                    booker = new Reservations(seatNum);
                    data = booker.checkSeats() + 1;
                    System.out.println(data);
                    flag = 1;
                }
            }

            //System.out.println();
            //Thread.sleep(5000);

            outToClient.writeBytes(data + "/" + flag);

            establishedSocket.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
