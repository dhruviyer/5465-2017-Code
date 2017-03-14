package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.*;

public class JoystickThread extends Thread
{
	private static Joystick leftDriveJoystick;
	private static Joystick rightDriveJoystick;
	
	static Timer timer = new Timer();
	
    static double forwardMag = 0.0;
	static double turnMag = 0.0;
	static double speed = 0;
	
	static boolean conveyShoot = false;
	static boolean conveyOn = false;
	
	static int LEFT_JOYSTICK_PORT = 0;
	static int RIGHT_JOYSTICK_PORT = 1;
	
	private boolean state;
	
	public JoystickThread(int leftPort, int rightPort)
	{
		leftDriveJoystick = new Joystick(leftPort);
    	rightDriveJoystick = new Joystick(rightPort);
    	this.LEFT_JOYSTICK_PORT = leftPort;
    	this.RIGHT_JOYSTICK_PORT = rightPort;
    	
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
    	}
    	else
    	{
    		turnMag = leftDriveJoystick.getX();
    	}
    	
    	speed = -1 *leftDriveJoystick.getThrottle();
    	
    	if(leftDriveJoystick.getRawButton(5)) conveyShoot = !conveyShoot;
    	
    	if(leftDriveJoystick.getRawButton(1)) conveyOn = !conveyOn;
    	
    	speed = leftDriveJoystick.getThrottle();
    	
    	Timer.delay(0.175);
    }
	
	public void stopJoy()
	{
		forwardMag = 0;
		turnMag = 0;
		speed = 0;
		conveyShoot = false;
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
	
	public double getSpeed()
	{
		return speed;
	}
	
	public boolean getConveyorShoot()
	{
		return conveyShoot;
	}
	
	public boolean getConveyor()
	{
		return conveyOn;
	}
	
	
	
	
}
