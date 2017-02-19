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
		String input = receiveSentence;
		
		if(input.contains("Y"))
		{
			//Sample String: Y 167 85 7.009
			String[] array = input.split("\\s+");
			double centerx = Integer.parseInt(array[1]);
			double centery = Integer.parseInt(array[2]);
			double height = Double.parseDouble(array[3]);
			double width = Double.parseDouble(array[4]);
			//System.out.println(array[1]);
			
			double heightreal = Math.min(height, width);
			double widthreal = Math.max(height, width);
			double distance = calcWidthDistance(widthreal);
			//System.out.println(centerx + " " + centery + " " + heightreal + " " + widthreal);
			return Double.toString(distance);
		}
		else
		{
			return ("Nothing");
		}
		
	}
	
	public static double calcWidthDistance(double x)
	{
		return -0.0003* Math.pow(x, 3) + 0.0881 * x * x - 10.336*x + 553.9;
	}
	
	public static double calcHeightDistance(double x)
	{
		return -0.0136 * Math.pow(x, 3) + 1.2378* x * x - 41.4* x + 596.2;
	}
}
