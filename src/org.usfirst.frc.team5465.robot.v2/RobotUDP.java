package org.usfirst.frc.team5465.robot;

import java.io.*;
import java.net.*;

public class RobotUDP extends Thread
{
	private String name;
	private DatagramSocket serverSocket;
	private byte[] receiveData;
	private String receiveSentence;
	private double distance;
	private int centerx;
	private int centery;
	private boolean inframe;
	public RobotUDP(String name1, int port, int buffersize) 
	{
		super(name1);
		distance = 0;
		centerx = 0;
		centery = 0;
		inframe = false;
		
		try 
		{
			serverSocket = new DatagramSocket(port);
		} 
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		receiveData = new byte[buffersize];
		receiveSentence = "";
	}
	
	public void run()
	{
		while(true)
        {
		
           DatagramPacket receivePacket = new DatagramPacket(receiveData, 0,receiveData.length);
          
           try 
           {
        	   serverSocket.receive(receivePacket);
			
           } 
           catch (IOException e) 
           {
        	   e.printStackTrace();
           }
           
           receiveSentence = new String(receivePacket.getData());
           
           if(receiveSentence.contains("Y"))
           {
	   			//Sample String: Y 167 85 7.009
        	    inframe = true;
	   			String[] array = receiveSentence.split("\\s+");
	   			centerx = Integer.parseInt(array[1]);
	   			centery = Integer.parseInt(array[2]);
	   			double height = Double.parseDouble(array[3]);
	   			double width = Double.parseDouble(array[4]);
	   			
	   			double widthreal = Math.max(height, width);
	   			distance = calcWidthDistance(widthreal);
	   		}
           
	   		else
	   		{
	   			inframe = false;
	   			distance = 0;
	   			centerx = 0;
	   			centery = 0;
	   		}
	   	
        }
	}
	
	public String getRawSentence()
	{
		return receiveSentence;
	}
	public double getDistance()
	{
		return distance;
	}
	public int getCenterX()
	{
		return centerx;
	}
	public int getCenterY()
	{
		return centery;
	}
	public boolean getInframe()
	{
		return inframe;
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
