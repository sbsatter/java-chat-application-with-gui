package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;














import javax.sound.midi.Receiver;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import view.Main;

public class Client extends Thread {
	private static final String TAG="Client: ";
	private Socket socket;
	public static HashMap<String, Socket> clientSockets= new HashMap<String, Socket>();
	int port;
	String url= "127.0.0.1";
	String name= "";
	int id;
	PrintWriter pw;
	private Server server;
	ReceiveMessageThread rmt;
	SendMessageThread smt;
	static int x_position=0;
	JFrame clientFrame;
	JPanel panel;
	JButton sendButton;
	JTextField chatField;
	
	private Client(String name, int port) {
		// TODO Auto-generated constructor stub
		super(name);
		this.port= port;
	}

	public Client(String name, int id, int port, Server server) throws IOException{
		this(name,port);
		this.server=server;
		server.CURRENTLY_CONNECTED=name;
		socket = new Socket(url, port);
		System.out.println(TAG+"Socket init for "+name);
		clientSockets.put(name, socket);
		this.name= name;
		this.id=id;
		initFrame();
	}

	private void initFrame() {
		// TODO Auto-generated method stub
		clientFrame= new JFrame(name);
		clientFrame.setSize(500, 820);
		clientFrame.setLayout(null);
		clientFrame.setLocation(Main.x_position, 0);
		chatField= new JTextField();
		chatField.setBounds(20,600,370,160);
		clientFrame.setResizable(false);
		clientFrame.add(chatField);
		panel= new JPanel();
		panel.setBounds(20, 20, 460,560);
		panel.setBackground(new Color(0xffffff));
		clientFrame.add(panel);
		sendButton= new JButton("SEND");
		sendButton.setBounds(410,635,70,50);
		
		clientFrame.add(sendButton);
		Main.x_position+=500;
		clientFrame.setVisible(true);
	}

	@Override
	public void run() {
		System.out.println(TAG+name+" has connected!");
		rmt= new ReceiveMessageThread(name, socket,panel, clientFrame);
		smt= new SendMessageThread(name, socket, panel, clientFrame,sendButton, chatField);
		rmt.start();
		smt.start();



	}



	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
