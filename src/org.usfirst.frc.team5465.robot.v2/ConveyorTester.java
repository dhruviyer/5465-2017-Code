package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.Talon;

public class ConveyorTester extends Thread
{
	private Talon conveyor1;
	private Talon conveyor2;
	
	private boolean go;
	private double speed;
	public ConveyorTester(int conveyor1port, int conveyor2port)
	{
		conveyor1 = new Talon(conveyor1port);
		conveyor2 = new Talon(conveyor2port);
		
		go = false;
		speed = 0;
	}
	
	public void run()
	{
		while(true)
		{
			if(go) this.move();
			else this.idle();
		}
	}
	
	public void move()
	{
		conveyor1.set(speed);
		conveyor2.set(speed);
	}
	
	public void idle()
	{
		conveyor1.set(0);
		conveyor2.set(0);
	}
	public void changeState(boolean x)
	{
		go = x;
	}
	public void updateVals(double x)
	{
		speed = x;
	}
}
