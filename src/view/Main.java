package view;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import controller.Client;
import controller.Server;

public class Main {
	private static final String TAG="MAIN: ";
	public static int x_position=0;
	public static final int PORT=9999;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Server server= new Server();
		server.start();
		Scanner scanner= new Scanner(System.in);
		System.out.println(TAG+"Enter number of clients: ");
		int numberOfClients=scanner.nextInt();
		scanner=null;
		scanner=new Scanner(System.in);
		HashMap<String, Client> clients= Server.clients;
		String []names= {"aa","bb","cc"};
		//		System.out.println(TAG+"Enter the number of clients: ");
		for(int i=0; i<numberOfClients; i++){
//			String name= names[i];
			String name=scanner.nextLine();
			System.out.print(TAG+"Name of client ("+i+"): "+name);
			if(name.equals(""))
				System.out.println(TAG+"name not reg properly");
			Client c;
			try {
				c = new Client(name,i,PORT,server);
				clients.put(name, c);
				c.start();

				Thread.sleep(2000);                 //1000 milliseconds is one second.

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}



	}
}
