package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.*;

public class RobotDrive 
{
	protected static Victor leftSide;
	protected static Victor rightSide;
	
	public RobotDrive(int leftPort, int rightPort) 
	{
		leftSide = new Victor(leftPort);
		rightSide = new Victor(rightPort);
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
	
	public void drive(double left, double right)
	{
		leftSide.set(left);
		rightSide.set(right);
	}
	
}
