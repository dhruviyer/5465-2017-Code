package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.*;
import java.io.*;

public class RobotAggetator extends Thread
{
	protected Talon aggetator;
	protected boolean state;
	
	public RobotAggetator(int aggetatorAddress)
	{
		//aggetatorAddress: PWM Channel
		aggetator = new Talon(aggetatorAddress);
		state = false;
	}

	public void run()
	{
		//System.out.println("starting agitator");
		while(true)
		{
			if(state) aggetator.set(1);
			else aggetator.set(0);
		}
	}
	
	public void updateState(boolean a)
	{
		state = a;
	}
}
