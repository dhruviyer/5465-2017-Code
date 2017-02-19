package org.usfirst.frc.team54652017.robot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class RobotUDP extends Thread
{
	private String name;
	private DatagramSocket serverSocket;
	private byte[] receiveData;
	private String receiveSentence;
	private boolean keepgoing;
	
	public RobotUDP(String name1, int port, int buffersize) 
	{
		super(name1);
		
		try 
		{
			serverSocket = new DatagramSocket(port);
			System.out.println("Starting Server");
		} 
		catch (SocketException e)
		{
			System.out.println("UDP Server can't be made");
		}
		receiveData = new byte[buffersize];
		receiveSentence = "";
	}
	
	public void run()
	{
		System.out.println("Starting Thread");
		while(true)
        {
		
           DatagramPacket receivePacket = new DatagramPacket(receiveData, 0,receiveData.length);
          
           try 
           {
        	   serverSocket.receive(receivePacket);
			
           } 
           catch (IOException e) 
           {
        	   System.out.println("hello bub");
        	   e.printStackTrace();
           }
           
           receiveSentence = new String(receivePacket.getData());
          
        }
	}
	
	public String getString()
	{
		return receiveSentence;
	}
}
