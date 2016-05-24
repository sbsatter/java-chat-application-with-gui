package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.Main;

public class ReceiveMessageThread extends Thread {
	Socket client;
	String name;
	JFrame frame;
	JPanel panel;
	int y=0;
	public ReceiveMessageThread(String name, Socket client, JPanel panel, JFrame clientFrame) {
		// TODO Auto-generated constructor stub
		super(name);
		this.client=client;
		this.name=name;
		this.frame= clientFrame;
		this.panel=panel;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
//			final JFrame frm= new JFrame();
//			JFrame frame= new JFrame(name);
//			frame.setSize(500, 500);
//			frame.setLayout(null);
//			frame.setLocation(Main.x_position, 0);
//			Main.x_position+=500;
//			frame.setVisible(true);
			BufferedReader br= new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			while(true){
				String receivedMsg= br.readLine();
//				System.out.println("SERVER>> "+receivedMsg);
				JLabel label= new JLabel(receivedMsg);
				label.setBounds(0,y,400,25);
				y+=25;
				panel.add(label);
				frame.repaint();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
