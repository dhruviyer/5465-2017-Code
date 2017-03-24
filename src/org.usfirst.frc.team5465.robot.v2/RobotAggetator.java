package org.usfirst.frc.team5465.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Timer;

public class RobotAggetator extends Thread
{
	private CANTalon aggetator;
	private boolean state;
	
	public RobotAggetator(int aggetatorAddress)
	{
		//aggetatorAddress: PWM Channel
		aggetator = new CANTalon(aggetatorAddress);
		state = false;
	}

	public void run()
	{
		//System.out.println("starting agitator");
		while(true)
		{
			if(state)
			{
				aggetator.set(0.25);
				Timer.delay(1);
				aggetator.set(-0.25);
				Timer.delay(1);
			}
			else aggetator.set(0);
		}
	}
	
	public void updateState(boolean a)
	{
		state = a;
	}
}
