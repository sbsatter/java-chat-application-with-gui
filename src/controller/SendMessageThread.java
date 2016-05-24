package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SendMessageThread extends Thread {
	Socket client;
	String name;
	JPanel panel;
	JFrame frame;
	JButton btn;
	JTextField textf;
	public SendMessageThread(String name, Socket client, JPanel panel, JFrame clientFrame, JButton btn, JTextField text) {
		// TODO Auto-generated constructor stub
		super(name);
		this.client=client;
		this.name=name;
		this.panel= panel;
		this.frame= clientFrame;
		this.btn=btn;
		this.textf=text;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String s=textf.getText().toString();
				PrintWriter pw = null;
				try {
					pw = new PrintWriter(client.getOutputStream(),true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				s=name+":"+s;
				pw.println(s);
				textf.setText("");
			}
		});
		textf.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					String s=textf.getText().toString();
					PrintWriter pw = null;
					try {
						pw = new PrintWriter(client.getOutputStream(),true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					s=name+":"+s;
					pw.println(s);
					textf.setText("");
				}
			}
		});




	}

}
