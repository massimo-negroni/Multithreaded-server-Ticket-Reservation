import java.io.*;
import java.net.*;
import java.util.Scanner;

import static java.lang.System.*;

class TCPClient {
	public static void main(String[] args) throws Exception {
		String address = args[0];
		int port = Integer.parseInt(args[1]);
		String name = null;
		String ticketNumber = String.valueOf(0);
		String result;
		String[] data;
		boolean flag = true;

		Scanner sc = new Scanner(in);

		Socket client = null;
		try {
			out.println("Attempt connection...");
			client = new Socket(address, port);
			out.println("Connection established\n");
		} catch (Exception e) {
			out.println("Connection refused\n");
		}

		assert client != null;
		DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

		do {
			try {
				out.println("Insert your name: ");
				name = sc.nextLine();
				out.println("Insert the number of tickets: ");
				ticketNumber = sc.nextLine();
				Integer.parseInt(ticketNumber);
				if(Integer.parseInt(ticketNumber) > 0){
					flag = false;
				}else{
					out.println("Insert a positive number");
				}
			} catch (Exception e) {
				out.println("Not a valid number");
				out.println(e.getMessage());
				flag = true;
			}
		} while(flag);

		outToServer.writeBytes(ticketNumber + "\n");

		result = inFromServer.readLine();
		data = result.split("/");
		if(Integer.parseInt(data[1]) == 0){
			System.out.println(name + " has booked " + ticketNumber + " tickets");
			System.out.println(Integer.parseInt(data[0]) + " free seats\n");
		} else{
			System.out.println("Not so many empty seats, there are only " + Integer.parseInt(data[0]) + " seats free\n");
		}

		client.close();
	}
}
