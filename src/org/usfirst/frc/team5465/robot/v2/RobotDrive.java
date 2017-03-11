package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.*;

/***
 * 
 * @author Dhruv
 * Handles all robot drive ops
 */
public class RobotDrive 
{
	protected Victor leftSide;
	protected Victor rightSide;
	
	protected ADXRS450_Gyro gyro;
	
	protected double setPoint;
	protected double currentValue;
	
	
	public RobotDrive(int leftPort, int rightPort, ADXRS450_Gyro gyro) 
	{
		leftSide = new Victor(leftPort);
		rightSide = new Victor(rightPort);
		this.gyro = gyro;
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
