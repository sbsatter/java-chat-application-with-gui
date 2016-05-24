package dummy_debugging;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Server {
	public static final int PORT= 1123;


	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket server= new ServerSocket(Server.PORT);
		Client client=new Client();
		client.start();
		Socket s= server.accept();
		Scanner sc= new Scanner(System.in);
		PrintWriter pw= new PrintWriter(s.getOutputStream(),true);
		new ReceiveMessageThread(s).start();
		while(true){
			pw.println(sc.nextLine());
		}

	}

}
class Client extends Thread{
	public Client() {
		// TODO Auto-generated constructor stub
		super("name");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		final JFrame frm= new JFrame();
		JFrame frame= new JFrame("name");
		frame.setSize(500, 500);
		frame.setLayout(null);
		frame.setVisible(true);
		super.run();
		try {

			Socket client= new Socket("localhost",Server.PORT);
			class SendMessageThread extends Thread{
				Socket client;
				public SendMessageThread(Socket client) {
					// TODO Auto-generated constructor stub
					this.client=client;
				}
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true){
						String s = (String)JOptionPane.showInputDialog(
								frm,
								"Send message to server");
						PrintWriter pw = null;
						try {
							pw = new PrintWriter(client.getOutputStream(),true);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						pw.println(s);
					}

				}

			}
			SendMessageThread smt= new SendMessageThread(client);
			smt.start();
			BufferedReader br= new BufferedReader(new InputStreamReader(client.getInputStream()));
			int y=50;
			while(true){
				String receivedMsg= br.readLine();
				System.out.println("SERVER>> "+receivedMsg);
				JLabel label= new JLabel("SERVER>> "+receivedMsg);
				label.setBounds(50,y,400,50);
				y+=50;
				frame.add(label);
				frame.repaint();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
class ReceiveMessageThread extends Thread{
	Socket client;
	public ReceiveMessageThread(Socket client) {
		// TODO Auto-generated constructor stub
		this.client=client;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			BufferedReader br;
			while(true){
				try {
					br= new BufferedReader(new InputStreamReader(client.getInputStream()));
					System.out.println(br.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

}