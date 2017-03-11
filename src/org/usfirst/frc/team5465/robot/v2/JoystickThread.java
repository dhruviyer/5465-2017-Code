package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class JoystickThread extends Thread
{
	private static Joystick leftDriveJoystick;
	private static Joystick rightDriveJoystick;
    static double forwardMag = 0.0;
	static double turnMag = 0.0;
	
	final static int LEFT_JOYSTICK_PORT = 0;
	final static int RIGHT_JOYSTICK_PORT = 5;
	static boolean setPointIsSet = false;
	static double setPoint = 0;
	static int encCount = 0;
	
	private boolean state;
	
	public JoystickThread()
	{
		leftDriveJoystick = new Joystick(LEFT_JOYSTICK_PORT);
    	rightDriveJoystick = new Joystick(RIGHT_JOYSTICK_PORT);
    	state = false;
	}
	
	public void run()
	{
		while(true)
		{
			if(state) updateMags();
			else stopJoy();
		}
	}
	
	public void updateMags()
    {
    	if(leftDriveJoystick.getRawButton(1))
    	{
    		forwardMag = rightDriveJoystick.getY()*0.25;
    	}
    	else
    	{
    		forwardMag = rightDriveJoystick.getY();
    	}
    	
    	if(rightDriveJoystick.getRawButton(1))
    	{
    		turnMag = leftDriveJoystick.getX()*0.25;
    	}else
    	{
    		turnMag = leftDriveJoystick.getX();
    	}
    }
	
	public void stopJoy()
	{
		forwardMag = 0;
		turnMag = 0;
	}
	public void changeState(boolean x)
	{
		state = x;
	}
	
	public double getForward()
	{
		return forwardMag;
	}
	
	public double getTurn()
	{
		return turnMag;
	}
	
	
	
}
