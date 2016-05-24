package controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import view.Main;

public class Server extends Thread{
	public static final int PORT=9999;
	public static final String TAG="SERVER: ";
	static String SENDER;
	public static Socket socket;
	public static ServerSocket listenerSocket= null;
	public static HashMap<String, Client> clients;
	public static HashMap<String, Socket> sockets;
	public static ArrayList<ServerThread> threads= new ArrayList<ServerThread>();
	public static String CURRENTLY_CONNECTED;
	public Server() throws IOException {
		// TODO Auto-generated constructor stub
		init();
	}

	public void init() throws IOException {
		// TODO Auto-generated method stub
		sockets=new HashMap<String, Socket>();
		clients=new HashMap<String, Client>();
		listenerSocket= new ServerSocket(PORT);
		CURRENTLY_CONNECTED="";
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println(TAG+"Server Thread Started");
		
		while(true){
			Socket s;
			try {
				s = listenerSocket.accept();
				socket=s;
				ServerThread st= new ServerThread(CURRENTLY_CONNECTED,s);
				threads.add(st);
				st.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}

class ServerThread extends Thread{
	Socket socket;
	private static String TAG=" ServerThread: ";
	private String name;
	public ServerThread(String name, Socket socket) {
		// TODO Auto-generated constructor stub
		super(name);
		this.socket=socket;
		this.name=name;
//		TAG= this.name+TAG;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println(TAG+"ServerThread started="+name);
		while(true){
			try {
//				int debug=5;
				BufferedReader br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while(true){
					String incomingMessage= br.readLine();
					if(incomingMessage!=null){
						System.out.println(TAG+name+" INCOMING: "+incomingMessage);
						String[] messages= incomingMessage.split(":");
						String message= messages[2];
						String receiver= messages[1];
						String sender= messages[0];
//						String message=incomingMessage.substring(incomingMessage.indexOf(':')+1, incomingMessage.length());
//						String receiver=incomingMessage.substring(incomingMessage.indexOf(':')+1, incomingMessage.lastIndexOf(':'));
						System.out.println(TAG+name+":"+receiver+">>"+message);
						sendMessage(receiver,sender,message);
//						System.exit(0);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void sendMessage(String receiver, String sender, String message) throws IOException {
		// TODO Auto-generated method stub
		Iterator<ServerThread> it= Server.threads.iterator();
		Socket receiverSocket=null;
		System.out.println(TAG+" size of threads_list: "+Server.threads.size());
		while(it.hasNext()){
			ServerThread st= it.next();
			System.out.println(TAG+st.getName()+ "=="+receiver);
			if(st.getName().equals(receiver)){
				receiverSocket= st.getSocket();
				break;
			}
		}
		PrintWriter pw= new PrintWriter(receiverSocket.getOutputStream(),true);
		pw.println(sender+">>"+message);
		System.out.println(TAG+" MESSAGE SENT TO "+receiver);
	}
	private Socket getSocket() {
		// TODO Auto-generated method stub
		return socket;
	}

}