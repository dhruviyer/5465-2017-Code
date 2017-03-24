package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.*;

public class JoystickThread extends Thread
{
	private static Joystick leftDriveJoystick;
	private static Joystick rightDriveJoystick;
	
	static Timer timer = new Timer();
	
    static double forwardMag = 0.0;
	static double turnMag = 0.0;
	static double leftspeed = 0;
	static double rightSpeed = 0;
	
	static boolean conveyor = false;
	static boolean shooter = false;
	static boolean auto = false;
	
	static int LEFT_JOYSTICK_PORT = 0;
	static int RIGHT_JOYSTICK_PORT = 1;
	
	private boolean state;
	
	public JoystickThread(int leftPort, int rightPort)
	{
		leftDriveJoystick = new Joystick(leftPort);
    	rightDriveJoystick = new Joystick(rightPort);
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
    	if(leftDriveJoystick.getRawButton(2))
    	{
    		forwardMag = rightDriveJoystick.getY()*0.25;
    	}
    	else
    	{
    		forwardMag = rightDriveJoystick.getY();
    	}
    	
    	if(rightDriveJoystick.getRawButton(2))
    	{
    		turnMag = leftDriveJoystick.getX()*0.25;
    	}
    	else
    	{
    		turnMag = leftDriveJoystick.getX();
    	}
    	    	
    	if(leftDriveJoystick.getRawButton(5)) conveyor = !conveyor;
    	    	
    	shooter = leftDriveJoystick.getRawButton(1);
    	
    	if(shooter) leftspeed = leftDriveJoystick.getThrottle();
    	else leftspeed = 0;
    	
    	rightSpeed = rightDriveJoystick.getThrottle();
    	auto = rightDriveJoystick.getRawButton(1);
    	
    	Timer.delay(0.175);
    }
	
	public void stopJoy()
	{
		forwardMag = 0;
		turnMag = 0;
		conveyor = false;
		shooter = false;
		leftspeed = 0;
		rightSpeed = 0;
		
	}
	
	public void changeState(boolean x)
	{
		state = x;
	}
	
	public boolean getShooter()
	{
		return shooter;
	}
	public double getForward()
	{
		return forwardMag;
	}
	
	public double getTurn()
	{
		return turnMag;
	}
	public boolean getConveyor()
	{
		return conveyor;
	}
	public double getSpeed()
	{
		return leftspeed;
	}
	
	public double getRightSpeed()
	{
		return rightSpeed;
	}
	
	public boolean getAuto()
	{
		return auto;
	}
	
}
