import java.net.ServerSocket;
import java.net.Socket;

class TCPDispatcher {

    public static void main(String[] args) throws Exception
    {
        int port = Integer.parseInt(args[0]);

        ServerSocket listeningSocket = new ServerSocket(port);

        while(true) {

            Socket establishedSocket = listeningSocket.accept();
            System.out.println(establishedSocket.toString());

            TCPServerWorker thread = new TCPServerWorker(establishedSocket);

            thread.start();
        }
    }
}
