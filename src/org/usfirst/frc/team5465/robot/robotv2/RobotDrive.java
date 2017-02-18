package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.*;
import java.io.*;

/***
 * 
 * @author Dhruv
 * Handles all robot drive ops
 */
public class RobotDrive extends Thread
{
	protected Victor leftSide;
	protected Victor rightSide;
	
	protected ADXRS450_Gyro gyro;
	
	protected double setPoint;
	protected double currentValue;
	protected int state;
	protected double first;
	protected double second;
	
	public RobotDrive(int leftPort, int rightPort, ADXRS450_Gyro gyro) 
	{
		leftSide = new Victor(leftPort);
		rightSide = new Victor(rightPort);
		this.gyro = gyro;
		state = 0;
		first = 0;
		second = 0;
	}
	
	/**state:
	 * 0: STOP
	 * 1: DRIVE STRAIGHT
	 * 2: DRIVE 
	 * 
	 * Depending on state first second can be:
	 *
	 * state= 0
	 * 	first = 0
	 * 	second 0
	 * 
	 * state = 1
	 * 	first = setPoint
	 * 	second = forwardMag
	 * 
	 * state = 2
	 * 	first = -1*forwardMag+turnMag
	 * 	second = forwardMag+turnMag
	 */
	
	public void run()
	{
		System.out.println("Starting Drive Thread");
		
		while(true)
		{
			if(state==1)
			{
				this.driveStraight(first, second);
			}
			else if(state ==2)
			{
				this.drive(first, second);
			}
			else if(state == 0)
			{
				this.stopMotors();
			}
		}
	}
	
	
	public void updateVals(int x, double x1, double x2)
	{
		state = x;
		first = x1;
		second = x2;
	}
	
	public void stopMotors()
	{
		leftSide.set(0);
		rightSide.set(0);
	}
	
	public double getLeftMotorVal()
	{
		return leftSide.getSpeed();
	}
	
	public double getRightMotorVal()
	{
		return rightSide.getSpeed();
	}
	
	/***
	 * 
	 * @param left
	 * @param right
	 * 
	 * Drive the motors using raw input
	 */
	
	public void drive(double left, double right)
	{
		leftSide.set(left);
		rightSide.set(right);
	}
	
	public void driveStraight(double setpoint, double speed)
	{
		updateSetPoint(setpoint);
		updateGyro();
		double difference = setPoint-currentValue;
		double motorPower = difference/180;
		drive(-speed+motorPower, speed+motorPower);
	}
	
	public void updateGyro()
	{
		currentValue = gyro.getAngle();
	}
	
	public void updateSetPoint(double setPoint)
	{
		this.setPoint = setPoint;
	}
	
	public double getSetPoint()
	{
		return setPoint;
	}	
}
