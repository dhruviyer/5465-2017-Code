package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.*;
import java.io.*;

public class RobotConveyClimb extends Thread
{
	protected Talon mover;
	protected Solenoid switchState;
	protected boolean state;
	protected boolean on;
	
	public RobotConveyClimb(int talonConveyAddress, int solenoidSwitch)
	{
		//talonConveyAddess: PWM Channel
		//solenoidSwitch: PCM Channel
		mover = new Talon(talonConveyAddress);
		switchState = new Solenoid(solenoidSwitch);
		on = false;
		state = false;
	}
	
	public void run()
	{
		System.out.println("Starting Conveyer");
		
		while(true)
		{
			//State True: Means Conveyer, State False: Means Climber 
			this.move();
		}
	}
	
	public void move()
	{
		if(on)
		{
			if(!state)
			{
				switchState.set(true);
				//turns the solenoid to config of conveyor
				mover.set(1);
			}
			
			else
			{
				switchState.set(false);
				//switch from conveyor to climber
				mover.set(1);
			}
		}
		else
		{
			mover.set(0);
		}
	}
	
	public void startstop(boolean on)
	{
		this.on = on;
	}
	public void updateState(boolean state)
	{
		this.state = state;
	}
}
